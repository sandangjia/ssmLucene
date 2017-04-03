package com.erinic.ssm.lucene;
import com.erinic.ssm.domain.User;
import com.erinic.ssm.utils.StringUtil;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.StringReader;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/2 0002.
 */
public class LuceneIndex {

   private Directory directory;


   private IndexWriter getIndex() throws Exception{
       directory = FSDirectory.open(Paths.get("c://lucene"));
       SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
       IndexWriterConfig config = new IndexWriterConfig(analyzer);
       IndexWriter indexWriter = new IndexWriter(directory,config);
       return indexWriter;
   }


   public void addIndex(User user) throws Exception{
       IndexWriter indexWriter = getIndex();
       Document document = new Document();
       document.add(new StringField("id",String.valueOf(user.getUserId()), Field.Store.YES));
       document.add(new TextField("username",user.getUsername(), Field.Store.YES));
       document.add(new TextField("description",user.getDescription(), Field.Store.YES));
       indexWriter.addDocument(document);
       indexWriter.close();
   }


   public void updateIndex(User user) throws Exception{
       IndexWriter indexWriter = getIndex();
       Document document = new Document();
       document.add(new StringField("id",String.valueOf(user.getUserId()),Field.Store.YES));
       document.add(new TextField("username",user.getUsername(), Field.Store.YES));
       document.add(new TextField("description",user.getDescription(), Field.Store.YES));
       indexWriter.updateDocument(new Term("id",String.valueOf(user.getUserId())),document);
   }

   public void removeIndex(String userId) throws Exception{
       IndexWriter indexWriter = getIndex();
       indexWriter.deleteDocuments(new Term("id",userId));
       indexWriter.forceMergeDeletes();
       indexWriter.commit();
       indexWriter.close();
   }

   public List<User> selecUser(String q) throws Exception{
       directory = FSDirectory.open(Paths.get("C://lucene"));
       IndexReader indexReader = DirectoryReader.open(directory);
       IndexSearcher indexSearcher = new IndexSearcher(indexReader);
       BooleanQuery.Builder builder = new BooleanQuery.Builder();
       SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();

       QueryParser parser = new QueryParser("username",analyzer);
       Query query = parser.parse(q);

       QueryParser parserDes = new QueryParser("description",analyzer);
       Query queryDes = parserDes.parse(q);

       builder.add(query, BooleanClause.Occur.SHOULD);
       builder.add(queryDes,BooleanClause.Occur.SHOULD);

       TopDocs hits = indexSearcher.search(builder.build(),100);
       QueryScorer scorer = new QueryScorer(query);
       Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);

       /**
        *
        */
       SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<b><font color = 'red'>","</font></b>");
       Highlighter highlighter = new Highlighter(simpleHTMLFormatter,scorer);
       highlighter.setTextFragmenter(fragmenter);
       List<User> users = new LinkedList<>();
       for (ScoreDoc scoreDoc : hits.scoreDocs){
           Document doc = indexSearcher.doc(scoreDoc.doc);
           User user = new User();
           user.setUserId(Integer.parseInt(doc.get("id")));
//           user.setUsername(doc.get("username"));
           String username = doc.get("username");
           String description = doc.get("description");
           if (username != null){
               TokenStream tokenStream = analyzer.tokenStream("username",new StringReader(username));
               String hUsername = highlighter.getBestFragment(tokenStream,username);
               if (StringUtil.isEmpty(hUsername)){
                   user.setUsername(username);
               }else {
                   user.setUsername(hUsername);
               }
           }
           if (description != null){
               TokenStream tokenStream = analyzer.tokenStream("description", new StringReader(description));
               String hDes = highlighter.getBestFragment(tokenStream,description);
               if (StringUtil.isEmpty(hDes)){
                   if (description.length() < 200){
                       user.setDescription(description);
                   }else {
                       user.setDescription(description.substring(0,200));
                   }
               }else {
                   user.setDescription(hDes);
               }
           }
           users.add(user);
       }

       return users;
   }
}

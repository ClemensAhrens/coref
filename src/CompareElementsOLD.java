// CompareElements.java

import java.util.*;
import java.io.*;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

public class CompareElements
{
  public static void main( String[] args )
  {
    if( args.length != 2 ) //TODO
    {
    	System.err.println( "Attention:" );
        System.err.println( "jdom.jar must be added to classpath." );
        System.err.println( "Usage:" );
        System.err.println( "java CompareElements <XmlFile> <NewFile>" );
        System.err.println( "Example:" );
        System.err.println( "java -classpath ../*/jdom.jar CompareElements"
                              + " input.xml output.xml");
      System.exit( 1 );
    }
    try {
      // ---- Read XML file ----
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build( new File( args[0] ) );  // <XmlFile>
//      String filename = args[1];
//      Document outputdoc = new SAXBuilder().build( filename );
      Document outputdoc = new Document( new Element( "root" ) );
//      Document outputdoc = builder.build( new File( args[1] ) );  // <NewFile>
      
      // ---- Create list of <coreferences> 
      Element root = doc.getRootElement();
      Element document = root.getChild("document");
      String chapID = document.getAttribute("chap").getValue(); //extract chapID for outputdoc
      Element corefs = document.getChild("coreferences");
      List listcoref = corefs.getChildren("coreference");
      for( int i=0; i<listcoref.size(); i++ )
      {      
      // ---- create list of <mention> for each listcoref
    	  Element coref  = (Element)(listcoref.get( i ));
    	  String corefID = coref.getAttribute("id").getValue(); //extract corefID for outputdoc
    	  List listmention = coref.getChildren("mention");//TODO
    	  
    	  for( int j=0; j<listmention.size(); j++ )
          {  
    	// Find <mention> with given attribute representative="true":
          Element ment  = (Element)(listmention.get( j ));
          if( null == ment )  continue;
          String s = ment.getAttributeValue("representative");     // <AttrS>
          if( null == s || !s.equals("true") )  continue;          // <ValS>
          String text = ment.getChildText("text");

        // test check for correct xml-element:
        ment.setAttribute( "testattr", "testval" );            // <AttrNew>=<ValNew>

        // ---- create elements for outputdoc
        Element newChains = new Element("chains");
        Element newChain = new Element("chain");
        Element newCoref = new Element("coreference");
        Element newChapter = new Element("chapter");
        Element newID = new Element("id");
//        Element newMention = ment;
        
        // ---- Write result to ouputdoc ---- TODO: find solution for id counter in attribute id
        Element outroot = outputdoc.getRootElement();
        outroot.addContent( newChains );
        newChains.addContent(newChain);
//        String k = 0;
//		newChain.setAttribute("id", k);
        newChain.setAttribute("reprText", text); //insert <text> from representative <mention> as attribute
        newChain.addContent(newCoref);
        newCoref.addContent(newChapter);
        newChapter.addContent(chapID);
        newCoref.addContent(newID);
        newID.addContent(corefID);
        
        XMLOutputter outp = new XMLOutputter();
        outp.setFormat( Format.getPrettyFormat() );
        // ---- Show the modified element on the screen ----
        System.out.println( "\nModified Element:\n" );
        outp.output( ment, System.out );
        System.out.println();
        // ---- Write the complete result document to XML file ----
//        XMLOutputter out = new XMLOutputter();
//        out.output( outputdoc, System.out );
        outp.output( outputdoc, new FileOutputStream( new File( args[1] ) ) );
        break;                                              // <NewFile>
      }
    }} catch( Exception ex ) {
      ex.printStackTrace();
    }
  
}}
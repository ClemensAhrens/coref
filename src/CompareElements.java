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
//    if( args.length != 2 ) //TODO
//    {
//    	System.err.println( "Attention:" );
//        System.err.println( "jdom.jar must be added to classpath." );
//        System.err.println( "Usage:" );
//        System.err.println( "java CompareElements <XmlFile> <NewFile>" );
//        System.err.println( "Example:" );
//        System.err.println( "java -classpath ../*/jdom.jar CompareElements"
//                              + " input.xml output.xml");
//      System.exit( 1 );
//    }
//    
    try {
    //	Erstellen der Outputdatei 
    Document outputdoc = new Document( new Element( "root" ) );
    	// Option zur Kennzeichnung der Outputdatei ( -o output.xml ) TODO
    
    	// write basic elements
    	Element newChains = new Element("chains");
    	Element outroot = outputdoc.getRootElement();
    	outroot.addContent( newChains );
    
    // read input directory & create file array
//    File inputdir = new File(args[0]);
//    File[] inputfiles = inputdir.listFiles();
    
    // for- schleife zum einlesen der inputs
//    for (File file : inputfiles) {
//        if (file.isFile()) {
//            System.out.println(file.getName());
        
    
    	// aufrufen des builders pro input
    	SAXBuilder builder = new SAXBuilder();
    	Document doc = builder.build( new File( args[0] ) );  // <XmlFile>
//    	Document outputdoc = new Document( new Element( "root" ) );
    	
    	// ---- Create list of <coreferences> & extract chapID
    	Element root = doc.getRootElement();
    	Element document = root.getChild("document");
    	String chapID = document.getAttribute("chap").getValue(); //extract chapID for outputdoc
    	Element corefs = document.getChild("coreferences");
    	List listcoref = corefs.getChildren("coreference");
    			
    	// ---- create list of <mention> for each listcoref & extract corefID
    	for( int i=0; i<listcoref.size(); i++ )
    	{      
    	Element coref  = (Element)(listcoref.get( i ));
    	String corefID = coref.getAttribute("id").getValue(); //extract corefID for outputdoc
    	List listmention = coref.getChildren("mention");   
    	
    	// Find <mention> with given attribute representative="true":
    	for( int j=0; j<listmention.size(); j++ )
        {  
        Element ment  = (Element)(listmention.get( j ));
        if( null == ment )  continue;
        String s = ment.getAttributeValue("representative");     // <AttrS
        if( null == s || !s.equals("true") )  continue;          // <ValS>
        String text = ment.getChildText("text");

        // test check for correct xml-element:
        ment.setAttribute( "testattr", "testval" );            // <AttrNew>=<ValNew>

    	// ---- create sub elements for each coref in outputdoc
    	Element newChain = new Element("chain");
    	Element newCoref = new Element("coreference");
    	Element newChapter = new Element("chapter");
    	Element newID = new Element("id");

    	// write results in output
        newChains.addContent(newChain);
        newChain.setAttribute("reprText", text); //insert <text> from representative <mention> as attribute
        newChain.addContent(newCoref);
        newCoref.addContent(newChapter);
        newChapter.addContent(chapID);
        newCoref.addContent(newID);
        newID.addContent(corefID);

        //    format output
        XMLOutputter outp = new XMLOutputter();
        outp.setFormat( Format.getPrettyFormat() );
        // ---- Show the modified element on the screen ----
        System.out.println( "\nModified Element:\n" );
        outp.output( ment, System.out );
        System.out.println();
        // ---- Write the complete result document to XML file ----
        outp.output( outputdoc, new FileOutputStream( new File( args[1] ) ) );
        break;                                              // <NewFile>
      }
    }} catch( Exception ex ) {
      ex.printStackTrace();
    }

  // funktion zum erstellen der basis output datei
//  public static void creOutput (String s) {
//	  File f = new File( s );
   
  } 
}
        //    output.setFormat( Format.getPrettyFormat() );
//    output.output( outputdoc, new FileOutputStream( new File( args[1] ) ) );
    
//    try {
//    	File inputdir = new File("./Input/");
//    	String[] fileNames = inputdir.list();
//    	File[]   fileNames = inputdir.listFiles();
//    	for(File k)
//    	System.out.println( "path: " + fileNames );

    	// ---- Read XML file ----
//    	SAXBuilder builder = new SAXBuilder();
//    	Document doc = builder.build( new File( args[0] ) );  // <XmlFile>
    	
    	//abfrage ob output schon existiert
//    	Document outputdoc = new Document( new Element( "root" ) );
//      Document outputdoc = builder.build( new File( args[1] ) );  // <NewFile>
      
//    	// ---- Create list of <coreferences> 
//    	Element root = doc.getRootElement();
//    	Element document = root.getChild("document");
//    	String chapID = document.getAttribute("chap").getValue(); //extract chapID for outputdoc
//    	Element corefs = document.getChild("coreferences");
//    	List listcoref = corefs.getChildren("coreference");
//    	
//    	for( int i=0; i<listcoref.size(); i++ )
//    	{      
//    	// ---- create list of <mention> for each listcoref
//    	Element coref  = (Element)(listcoref.get( i ));
//    	String corefID = coref.getAttribute("id").getValue(); //extract corefID for outputdoc
//    	List listmention = coref.getChildren("mention");//TODO
//    	  
//    	for( int j=0; j<listmention.size(); j++ )
//        {  
//    	// Find <mention> with given attribute representative="true":
//        Element ment  = (Element)(listmention.get( j ));
//        if( null == ment )  continue;
//        String s = ment.getAttributeValue("representative");     // <AttrS>
//        if( null == s || !s.equals("true") )  continue;          // <ValS>
//        String text = ment.getChildText("text");
//

        // ---- create elements for outputdoc
//        Element newChains = new Element("chains");
//        Element newChain = new Element("chain");
//        Element newCoref = new Element("coreference");
//        Element newChapter = new Element("chapter");
//        Element newID = new Element("id");
//      Element newMention = ment;
        
        // ---- Write result to ouputdoc ---- TODO: find solution for id counter in attribute id
//        Element outroot = outputdoc.getRootElement();
//        outroot.addContent( newChains );
//      String k = 0;
//		newChain.setAttribute("id", k);
//        newChains.addContent(newChain);
//        newChain.setAttribute("reprText", text); //insert <text> from representative <mention> as attribute
//        newChain.addContent(newCoref);
//        newCoref.addContent(newChapter);
//        newChapter.addContent(chapID);
//        newCoref.addContent(newID);
//        newID.addContent(corefID);
        
//        XMLOutputter outp = new XMLOutputter();
//        outp.setFormat( Format.getPrettyFormat() );
//        // ---- Show the modified element on the screen ----
//        System.out.println( "\nModified Element:\n" );
//        outp.output( ment, System.out );
//        System.out.println();
//        // ---- Write the complete result document to XML file ----
////        XMLOutputter out = new XMLOutputter();
////        out.output( outputdoc, System.out );
//        outp.output( outputdoc, new FileOutputStream( new File( args[1] ) ) );
//        break;                                              // <NewFile>
//      }
//    }} catch( Exception ex ) {
//      ex.printStackTrace();
//    }
//}
//}
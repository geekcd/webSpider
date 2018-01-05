package com.cd.main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
//дһ�������࣬�ṩһЩ������
   /*    �ṩ���췽�������Դ���һ���������
    *    �ṩһЩ������
    *    1.��������ַ��html�ļ�,��ָ�����ļ�����
    *    2.��������ַ�µĶ�Ӧ��Ϣ���ӣ�����һ���ַ������飩��
    *    3.����������Ϣ��ָ�����ļ����£�
    *    
    * 
    * */
  
class SimpleSpider {
					  //��Ա����
					  private String url;
					
					  //���캯��1
				      public SimpleSpider(String url) {
				    	 
				    	  this.url = url;
				        }
				     //�޲ι���
				      public SimpleSpider() {
						
	                   }

	//�����Ա��������Ŀ¼��ַ�µĵ�����ַ
      public ArrayList<String> get_SonUrl(String fatherurl){
    	  
			        ArrayList<String>  result = new ArrayList<>();
			   		
			 		  try{
							 			Document doc = Jsoup.connect(fatherurl).get();
							 			/* �õ�html�����ж���
							 			Element content = doc.getElementById("content");
							 			�����html��<img.../>֮������ж���
							 		    Elements links = content.getElementsByTag("a");
							 			 Elements links = doc.select("a[href]");
							 		      ��չ��Ϊ.png��ͼƬ
							 		    Elements pngs1 = doc.select("img[src$=.jpg]");*/
							 		/*�ҵ���������Ŀ¼ҳ���е�class = thumb�ı�ǩ  ������ҳ������������ǩ��Ӧ���ǽ�����ҳ������ӱ�ǩ��href=��Ӧ�ĵ�ַ*/
							 			Elements pngs = doc.select("[class=thumb]");
							 		
							 			for (Element png : pngs) {
							 				//��ȡ��<a></a>����ĵ�ַhref������
							 			     String linkson = png.attr("href");
							 			     
							 			//     System.out.println(linkson+"^^^^^^^^^linkson");
							 			     
							 			     result.add(linkson);
							 			     
										//	 System.out.println(linkson);
										}
						 		        
			 		        }
					 	catch(IOException e ){
					 			
					 					e.printStackTrace();
					 		}
				
			 		    return result;
		 			
		 		
      }
      
      //�����Ա��������������ַ�ڲ���������ЧͼƬ��ַ
      public ArrayList<String> get_ImgUrl(String url){
    	  
						    	  ArrayList<String>  result = new ArrayList<>();
						   		
						 		  try{
										 			Document doc = Jsoup.connect(url).get();
										 		
										 		/*�ҵ���������Ŀ¼ҳ���е�class = thumb�ı�ǩ  ������ҳ������������ǩ��Ӧ���ǽ�����ҳ������ӱ�ǩ��href=��Ӧ�ĵ�ַ*/
										
										 			Elements imgs = doc.select("img[src~=(?i)\\.(gif|jpe?g)]");
										 			
										 			for(Element img: imgs){
														
														String linkimg = img.attr("src");
														
														//System.out.println(linkimg+"**********img");
														
														 result.add(linkimg);
												
													}
						 		        }
								 	catch(IOException e ){
								 			
								 					e.printStackTrace();
								 		}
								 			
						 		    return result;	
						      }
     
     //�����Ա����������һҳ�ĸ�Ŀ¼��ַ��Ϣ
      public String get_NextUrl(String url){
 		  
			    	  String result = null;
			    	  
			 		  try{
			 		
							 			Document doc = Jsoup.connect(url).get();
							 		
							 		/*�ҵ���������Ŀ¼ҳ���е�class = thumb�ı�ǩ  ������ҳ������������ǩ��Ӧ���ǽ�����ҳ������ӱ�ǩ��href=��Ӧ�ĵ�ַ*/
							
							 			Element nextUrl = doc.select("a.next").first();
							 			
							 			String next= nextUrl.attr("href");
							 			
							    //	System.out.println(next+"--------next");
							 			
							 		    result = next;
						 	
			 		        }
					 	catch(IOException e ){
					 			
					 			     	e.printStackTrace();
					 		}
			 		     
				    	return result;
		 			
		 		
      }
      //��ȡ���ҳ��ı������� title
      public String get_Title(String url){
    	  
					    	  String result = null;
					   		
					 		  try{
									 			Document doc = Jsoup.connect(url).get();
									 		
									 		/*�ҵ���������Ŀ¼ҳ���е�class = thumb�ı�ǩ  ������ҳ������������ǩ��Ӧ���ǽ�����ҳ������ӱ�ǩ��href=��Ӧ�ĵ�ַ*/
									
									 	//		Elements title = content.getElementsByTag("title");
									 			Elements title = doc.select("title");
									 			
									 		//	System.out.println(title.text());
									 			
									 		    result = title.text().toString();
					 		        }
							 	catch(IOException e ){
							 			
							 			     	e.printStackTrace();
							 		}
					 		 	return result;		
							 		
      }
      
   
      
      
      //�����Ա�������������������ݣ�HTMLҳ�棬����ͼƬ���������֣�
      public  int  save_Data(String url,String dirfile,String sonfile,int i){
					  		
					  		File dest = null;
					  		
					  		String datetype = url.substring(url.length()-4);
					  		
					  		System.out.println("start the "+i+" picture download....");
					  		
					  	    long timeStart = System.currentTimeMillis();
					  		
					  		try{
					  			                File destdad = new File(dirfile+"/"+sonfile.substring(0,13 ));
					  			                //����һ���µ��ļ���
					  			                destdad.mkdir();
					  			                //������ļ������б�ǣ���ʱ��hashcodeֵ:
									  		    dest =new File(destdad.getAbsolutePath()+"/��ŮͼƬ"+i+datetype);
									  			
									  			//������������
									  			InputStream is;
									  			
									  			FileOutputStream fos = new FileOutputStream(dest);
									  			
									  			URL temp = new URL(url);
									  			
									  			is = temp.openStream();
									  			
									  			BufferedInputStream bis = new BufferedInputStream(is);
									  			
									  			BufferedOutputStream bos = new BufferedOutputStream(fos);
									  			
									  			int lenth;
									  			
									  			byte[] buf = new byte[1024*100];
									  			
									  			while((lenth=bis.read(buf))!=-1){
									  				
									  					fos.write(buf,0,lenth);
									  			}
									  			//�ر���Դ
									  			bos.close();
									  			
									  			fos.close();
									  			
									  			bis.close();
					  		}
					  		catch(IOException e){
					  			
					  			                System.out.println("�����쳣��");
					  			                
					  							e.printStackTrace();
					  			
					  		}
					  		//���ù�����ʵ��
					  /*		dest.listFiles(new FileFilter(){
					
								@Override
								public boolean accept(File pathname) {
									
								    if(pathname.length()<1024*50){
								    
								    pathname.delete();
								    	
									return false;}
								    
								    else {   return false;   }
								}
					  			
					  		});*/
					  	
					  		  //����ļ�С��50kb��ɾ����
						  		if(dest.length()<1024*50){
									
						  			dest.delete();
						  			
									i--;
								}
						  		
						  	  long timeEnd = System.currentTimeMillis();
						  	  
						  	  System.out.println("success ! spend "+(timeEnd-timeStart)/1000.0+" second.");
						  	  
						  		return i;
					  	
					  	}
  	
      
      
}
		 		
		 		



 
 		
     




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
//写一个爬虫类，提供一些方法：
   /*    提供构造方法，可以创建一个爬虫对象
    *    提供一些方法：
    *    1.获得这个网址的html文件,到指定的文件夹下
    *    2.获得这个网址下的对应信息链接（返回一个字符串数组）；
    *    3.下载数据信息到指定的文件夹下；
    *    
    * 
    * */
  
class SimpleSpider {
					  //成员变量
					  private String url;
					
					  //构造函数1
				      public SimpleSpider(String url) {
				    	 
				    	  this.url = url;
				        }
				     //无参构造
				      public SimpleSpider() {
						
	                   }

	//这个成员方法用来目录网址下的的子网址
      public ArrayList<String> get_SonUrl(String fatherurl){
    	  
			        ArrayList<String>  result = new ArrayList<>();
			   		
			 		  try{
							 			Document doc = Jsoup.connect(fatherurl).get();
							 			/* 得到html的所有东西
							 			Element content = doc.getElementById("content");
							 			分离出html下<img.../>之间的所有东西
							 		    Elements links = content.getElementsByTag("a");
							 			 Elements links = doc.select("a[href]");
							 		      扩展名为.png的图片
							 		    Elements pngs1 = doc.select("img[src$=.jpg]");*/
							 		/*找到福利汇总目录页面中的class = thumb的标签  ，根据页面分析，这个标签对应的是进入子页面的连接标签，href=对应的地址*/
							 			Elements pngs = doc.select("[class=thumb]");
							 		
							 			for (Element png : pngs) {
							 				//获取到<a></a>里面的地址href的内容
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
      
      //这个成员方法用来拿子网址内部的所有有效图片地址
      public ArrayList<String> get_ImgUrl(String url){
    	  
						    	  ArrayList<String>  result = new ArrayList<>();
						   		
						 		  try{
										 			Document doc = Jsoup.connect(url).get();
										 		
										 		/*找到福利汇总目录页面中的class = thumb的标签  ，根据页面分析，这个标签对应的是进入子页面的连接标签，href=对应的地址*/
										
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
     
     //这个成员方法用来下一页的父目录地址信息
      public String get_NextUrl(String url){
 		  
			    	  String result = null;
			    	  
			 		  try{
			 		
							 			Document doc = Jsoup.connect(url).get();
							 		
							 		/*找到福利汇总目录页面中的class = thumb的标签  ，根据页面分析，这个标签对应的是进入子页面的连接标签，href=对应的地址*/
							
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
      //获取这个页面的标题名称 title
      public String get_Title(String url){
    	  
					    	  String result = null;
					   		
					 		  try{
									 			Document doc = Jsoup.connect(url).get();
									 		
									 		/*找到福利汇总目录页面中的class = thumb的标签  ，根据页面分析，这个标签对应的是进入子页面的连接标签，href=对应的地址*/
									
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
      
   
      
      
      //这个成员方法用来下载所有数据（HTML页面，或者图片，或者文字）
      public  int  save_Data(String url,String dirfile,String sonfile,int i){
					  		
					  		File dest = null;
					  		
					  		String datetype = url.substring(url.length()-4);
					  		
					  		System.out.println("start the "+i+" picture download....");
					  		
					  	    long timeStart = System.currentTimeMillis();
					  		
					  		try{
					  			                File destdad = new File(dirfile+"/"+sonfile.substring(0,13 ));
					  			                //创建一个新的文件夹
					  			                destdad.mkdir();
					  			                //这里对文件名进行标记，暂时用hashcode值:
									  		    dest =new File(destdad.getAbsolutePath()+"/美女图片"+i+datetype);
									  			
									  			//创建流来保存
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
									  			//关闭资源
									  			bos.close();
									  			
									  			fos.close();
									  			
									  			bis.close();
					  		}
					  		catch(IOException e){
					  			
					  			                System.out.println("下载异常！");
					  			                
					  							e.printStackTrace();
					  			
					  		}
					  		//再用过滤器实现
					  /*		dest.listFiles(new FileFilter(){
					
								@Override
								public boolean accept(File pathname) {
									
								    if(pathname.length()<1024*50){
								    
								    pathname.delete();
								    	
									return false;}
								    
								    else {   return false;   }
								}
					  			
					  		});*/
					  	
					  		  //如果文件小于50kb则删除它
						  		if(dest.length()<1024*50){
									
						  			dest.delete();
						  			
									i--;
								}
						  		
						  	  long timeEnd = System.currentTimeMillis();
						  	  
						  	  System.out.println("success ! spend "+(timeEnd-timeStart)/1000.0+" second.");
						  	  
						  		return i;
					  	
					  	}
  	
      
      
}
		 		
		 		



 
 		
     




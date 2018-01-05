package com.cd.main;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

public class TestSpider {
	 //创建一个私有的爬虫对象
	 private  final SimpleSpider spider = new  SimpleSpider();
	 
	 static long sum =0 ;
	  
     @Test
	public  void Test() {
    	   //记录开始下载时间
					           long timeStart = System.currentTimeMillis();
						       //创建文件保存的父目录文件夹
					           File dirfile = new File("D:/Test");
					           
					           if(dirfile.exists()){
								//访问福利吧网站            http://fuliba.net/message.html         http://fuliba.net/category/flhz/page/11         
							   String url = "http://fuliba.net/category/flhz/page/2";
						      //开始下载 
						      download(dirfile.toString(),url);
						      //记录下载结束时间
							   long timeEnd = System.currentTimeMillis();  
							   //计算下载时间
							   long time = (timeEnd-timeStart)/1000;
							   
							   countSpace(dirfile);
							   //将字节转换为MB
							   sum=sum/(1024*1049);
							  	  
							   System.out.println("FileSpace："+sum+"MB，Alltime："+time+"，speed："+((time>0)?((int)(sum/time)):0)+"kb/s");
					           
					           }else    System.out.println("系统文件不存在哦！");
	}
     
     //计算文件大小，用来估算下载速度
     public void countSpace(File dirfile){
    
						  	  File[] files = dirfile.listFiles();
						  	  
						  	  for (File file : files) {
						  		  
						  		  if(file.isFile()){
						  		  
								  long l = file.length();
								  
								  sum+=l;
								  
								  }else {
									  countSpace(file);
									  
								  }
							}
    	 
     }
     
     
     //下载方法,这里是采用的递归的方法，父页面下爬取子目录和下一个页面，所有当前父页面的子页面内容都爬取完成后，开始爬取下一个父页面的所有信息。。。。
     public void download(String dirfile,String url){
    	 					//创建标题变量
	    	                String title = null;
			        	    //1.获取父页面的地址 ，第一次的地址手动输入
			    	        //2.得到该父页面下所有子页面的地址，并存到集合中
			    	       ArrayList<String> sonUrls = spider.get_SonUrl(url);
			    	        //开始遍历所有子页面的地址
			    	       for (String  sonUrl: sonUrls) {
			    	    	         //获得这个子页面的标题作为本地二级目录的文件名
				    	    	   title = spider.get_Title(sonUrl);
				    	    	    //获得单个子页面下的所有图片信息地址
				    	    	    ArrayList<String> imgUrls = spider.get_ImgUrl(sonUrl);
				    	    	    // 创建一个文件名标记变量
				    	    	    int i =1;
				    	    	    //开始遍历每一个图片地址，并下载
				    	       	    for (String imgUrl : imgUrls) {
									         //下载指定页面下图片
				    	    		   	if(spider.save_Data(imgUrl, dirfile,title,i)==i)
				    	    		   	{    //如果没有删除文件， 则i++，开始标记下一个文件，否则i,不动，赋值给下一个文件
				    	    		      	 i++;	
				    	    		      	 }
				    	    		   	System.out.println("--------------------------------");
				    	       	    	//System.out.println(imgUrl);
								      }
					    	}
			    	       //所有当前网站父页面的子网站的信息都提取完后，开始提取下一个父页面
			    	       String next = spider.get_NextUrl(url);
			    	       //如果next不为null ,则未到最后一页，则自己调用自己，递归
			               if( next!=null ){
			            	             
			            	        System.out.println(title+"'s file all have been downloaded。");
			    	    	        //递归调用
			    	    	       download(dirfile,next);
			    	       } 
			    	       else{
			    	    	       System.out.println("mission  over !");
			    	       }
			    	
	      }

     
}

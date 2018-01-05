package com.cd.main;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

public class TestSpider {
	 //����һ��˽�е��������
	 private  final SimpleSpider spider = new  SimpleSpider();
	 
	 static long sum =0 ;
	  
     @Test
	public  void Test() {
    	   //��¼��ʼ����ʱ��
					           long timeStart = System.currentTimeMillis();
						       //�����ļ�����ĸ�Ŀ¼�ļ���
					           File dirfile = new File("D:/Test");
					           
					           if(dirfile.exists()){
								//���ʸ�������վ            http://fuliba.net/message.html         http://fuliba.net/category/flhz/page/11         
							   String url = "http://fuliba.net/category/flhz/page/2";
						      //��ʼ���� 
						      download(dirfile.toString(),url);
						      //��¼���ؽ���ʱ��
							   long timeEnd = System.currentTimeMillis();  
							   //��������ʱ��
							   long time = (timeEnd-timeStart)/1000;
							   
							   countSpace(dirfile);
							   //���ֽ�ת��ΪMB
							   sum=sum/(1024*1049);
							  	  
							   System.out.println("FileSpace��"+sum+"MB��Alltime��"+time+"��speed��"+((time>0)?((int)(sum/time)):0)+"kb/s");
					           
					           }else    System.out.println("ϵͳ�ļ�������Ŷ��");
	}
     
     //�����ļ���С���������������ٶ�
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
     
     
     //���ط���,�����ǲ��õĵݹ�ķ�������ҳ������ȡ��Ŀ¼����һ��ҳ�棬���е�ǰ��ҳ�����ҳ�����ݶ���ȡ��ɺ󣬿�ʼ��ȡ��һ����ҳ���������Ϣ��������
     public void download(String dirfile,String url){
    	 					//�����������
	    	                String title = null;
			        	    //1.��ȡ��ҳ��ĵ�ַ ����һ�εĵ�ַ�ֶ�����
			    	        //2.�õ��ø�ҳ����������ҳ��ĵ�ַ�����浽������
			    	       ArrayList<String> sonUrls = spider.get_SonUrl(url);
			    	        //��ʼ����������ҳ��ĵ�ַ
			    	       for (String  sonUrl: sonUrls) {
			    	    	         //��������ҳ��ı�����Ϊ���ض���Ŀ¼���ļ���
				    	    	   title = spider.get_Title(sonUrl);
				    	    	    //��õ�����ҳ���µ�����ͼƬ��Ϣ��ַ
				    	    	    ArrayList<String> imgUrls = spider.get_ImgUrl(sonUrl);
				    	    	    // ����һ���ļ�����Ǳ���
				    	    	    int i =1;
				    	    	    //��ʼ����ÿһ��ͼƬ��ַ��������
				    	       	    for (String imgUrl : imgUrls) {
									         //����ָ��ҳ����ͼƬ
				    	    		   	if(spider.save_Data(imgUrl, dirfile,title,i)==i)
				    	    		   	{    //���û��ɾ���ļ��� ��i++����ʼ�����һ���ļ�������i,��������ֵ����һ���ļ�
				    	    		      	 i++;	
				    	    		      	 }
				    	    		   	System.out.println("--------------------------------");
				    	       	    	//System.out.println(imgUrl);
								      }
					    	}
			    	       //���е�ǰ��վ��ҳ�������վ����Ϣ����ȡ��󣬿�ʼ��ȡ��һ����ҳ��
			    	       String next = spider.get_NextUrl(url);
			    	       //���next��Ϊnull ,��δ�����һҳ�����Լ������Լ����ݹ�
			               if( next!=null ){
			            	             
			            	        System.out.println(title+"'s file all have been downloaded��");
			    	    	        //�ݹ����
			    	    	       download(dirfile,next);
			    	       } 
			    	       else{
			    	    	       System.out.println("mission  over !");
			    	       }
			    	
	      }

     
}

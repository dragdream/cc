/** 
 * @author CXT 
 */ 
 var  duduppp=function(){ 
    
           function  domReady(){ 
     var arr=new Array();  
      if(arguments.length>0){ 
           for (var i = 0; i < arguments.length; i++) {       
       arr[i]= arguments[i];       
     } 
      } 
                   if(document.all){ 
          
          document.onreadystatechange=function(){        
      /*document.readyState=="complete"||document.readyState=='interactive' 
       * 这样写会加载2次，先加载interactive 之后再加载complete 
       * 如过要求效率的话写成interactive, opera  没有反应，opera 没有interactive 直接到complete 
       * 所以 另写了 
       */ 
      if(document.readyState=='loaded'||document.readyState=="complete"){             
      //  alert(arr[0]+"------------IE--------------------"); 
       Start(arr);  
                          
                           }          
      } 
       } 
       else{   
       // alert(arr[0]+"------------NO--IE--------------------");       
          document.addEventListener("DOMContentLoaded",Start(arr),false); 
      }        
      function Start(arr){      
    for(var i=0;i<arr.length;i++){     
     if(typeof arr[i]=='function'){      
      arr[i](); 
     }      
    }              
            } 
               
            } 
   
      return {domReady:domReady 
               } 
           }();
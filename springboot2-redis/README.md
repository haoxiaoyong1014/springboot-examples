# springboot2-redis

springboot2x系列集成Redis

## how to use

* 基本功能
    
    
    String
    
    List
    
    HashMap
    
    Set
                
        
                    
* 在SpringBoot启动类上通过注解引入Main Class

        @Import({
                RedisMain.class
        })        
        
* 配置app key

        redis:
          hostName: 
          password:
          dbIndex: 0 
* 使用

       @Autowired
       private StringUtil stringUtil;
      
       stringUtil.set("name_1_2", "tom");
                           
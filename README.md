# jemo

<!-- Plugin description -->

将类json配置文件转为Dart model

通过增加注释的方式，来标记一些信息，如： 
- 设置可空不可空
  ```json
  {
    //nullable: false
    "total": 1  
  } 
  ```
- 设置别名
  ```json
  {
    //alias: counts, nullable: false
    "total": 1  
  } 
  ```
- 设置类型
  ```json
  {
    //class: School, alias: school
    "item": {
      "name": ""
    },
    //alias: students
    "list": [
      //class: Student
      {
        "name": ""
      }
    ]
  }
  ```

<!-- Plugin description end -->
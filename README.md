# JEMO

<!-- Plugin description -->

将json文件转为Dart代码，支持null safety. 

[项目地址](https://github.com/ejin66/jemo_plugin)

点击“Tools - Jemo Json Generate”或者“右键菜单 - Jemo Json Generate” 开始转。

> Tools中会自动将`lib/model/json/*.json`或`lib/src/mode/json/*.json`的所有json文件转换

> 或选择json文件，右键选择`Jemo Json Generate`，进行单个文件转换

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

# example
有如下类json配置文件`example.json`
```text
//import: XXX456
//import: XXX123

//class: First, super: SuperFirst
{
  //alias: aaa, nullable: true
  "a": 1.2,
  "b": false,
  //alias: ccc, nullable: false, class: SubC
  "c": {
    //class Inc, alias: inc, nullable: true
    "i": {
      "k": "123",
      "n": "111"
    },
    "d": 2,
    "e": "eee"
  },
  //alias: fff, nullable: true
  "f": [
    {
      "g": 22,
      "h": 33
    }
  ],
  "x": [
    //class: YZ
    {
      "y": 1,
      "z": false
    }
  ]
}

//class: Second
{
  //alias: username, nullable: false
  "user_name": "",
  "pass-word": "",
  //nullable: false, class: SubC
  "duplicate_model": {
    "d": 2,
    "e": "eee"
  },
  "abc": [
    1
  ]
}
```
生成的dart代码 `example.dart`:
```dart
/*
  Don't edit.
  Code generated by jemo.py.
*/

import 'dart:convert';
import 'XXX456';
import 'XXX123';

class First extends SuperFirst {
    double? aaa;
    bool? b;
    SubC ccc;
    List<SubItem1>? fff;
    List<YZ>? x;

    First({
        this.aaa,
        this.b,
        required this.ccc,
        this.fff,
        this.x,
    });

    factory First.fromRawJson(String str) => First.fromJson(json.decode(str));

    String toRawJson() => json.encode(toJson());

    factory First.fromJson(Map<String, dynamic> json) => First(
        aaa: json['a']?.toDouble(),
        b: json['b'],
        ccc: SubC.fromJson(json['c']),
        fff: json['f'] != null ? List<SubItem1>.from(json['f'].map((e) => SubItem1.fromJson(e))) : null,
        x: json['x'] != null ? List<YZ>.from(json['x'].map((e) => YZ.fromJson(e))) : null,
    );

    Map<String, dynamic> toJson() => {
        "a": aaa,
        "b": b,
        "c": ccc.toJson(),
        "f": fff != null ? List<dynamic>.from(fff!.map((e) => e.toJson())) : null,
        "x": x != null ? List<dynamic>.from(x!.map((e) => e.toJson())) : null,
    };
}

class SubC {
    Inc? inc;
    int? d;
    String? e;

    SubC({
        this.inc,
        this.d,
        this.e,
    });

    factory SubC.fromRawJson(String str) => SubC.fromJson(json.decode(str));

    String toRawJson() => json.encode(toJson());

    factory SubC.fromJson(Map<String, dynamic> json) => SubC(
        inc: json['i'] != null ? Inc.fromJson(json['i']) : null,
        d: json['d'],
        e: json['e'],
    );

    Map<String, dynamic> toJson() => {
        "i": inc?.toJson(),
        "d": d,
        "e": e,
    };
}

class Inc {
    String? k;
    String? n;

    Inc({
        this.k,
        this.n,
    });

    factory Inc.fromRawJson(String str) => Inc.fromJson(json.decode(str));

    String toRawJson() => json.encode(toJson());

    factory Inc.fromJson(Map<String, dynamic> json) => Inc(
        k: json['k'],
        n: json['n'],
    );

    Map<String, dynamic> toJson() => {
        "k": k,
        "n": n,
    };
}

class SubItem1 {
    int? g;
    int? h;

    SubItem1({
        this.g,
        this.h,
    });

    factory SubItem1.fromRawJson(String str) => SubItem1.fromJson(json.decode(str));

    String toRawJson() => json.encode(toJson());

    factory SubItem1.fromJson(Map<String, dynamic> json) => SubItem1(
        g: json['g'],
        h: json['h'],
    );

    Map<String, dynamic> toJson() => {
        "g": g,
        "h": h,
    };
}

class YZ {
    int? y;
    bool? z;

    YZ({
        this.y,
        this.z,
    });

    factory YZ.fromRawJson(String str) => YZ.fromJson(json.decode(str));

    String toRawJson() => json.encode(toJson());

    factory YZ.fromJson(Map<String, dynamic> json) => YZ(
        y: json['y'],
        z: json['z'],
    );

    Map<String, dynamic> toJson() => {
        "y": y,
        "z": z,
    };
}

class Second {
    String username;
    String? password;
    SubC duplicateModel;
    List<int>? abc;

    Second({
        required this.username,
        this.password,
        required this.duplicateModel,
        this.abc,
    });

    factory Second.fromRawJson(String str) => Second.fromJson(json.decode(str));

    String toRawJson() => json.encode(toJson());

    factory Second.fromJson(Map<String, dynamic> json) => Second(
        username: json['user_name'],
        password: json['pass-word'],
        duplicateModel: SubC.fromJson(json['duplicate_model']),
        abc: json['abc'] != null ? List<int>.from(json['abc'].map((e) => e)) : null,
    );

    Map<String, dynamic> toJson() => {
        "user_name": username,
        "pass-word": password,
        "duplicate_model": duplicateModel.toJson(),
        "abc": abc != null ? List<dynamic>.from(abc!.map((e) => e)) : null,
    };
}

```
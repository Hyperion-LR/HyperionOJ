# HyperionOJ接口文档

[TOC]



## oss单点登录

**（部分请求请携带请求头"SysUser-Token"）**

### 普通用户登录

接口url：localhost:9000/login/user

请求方式： post

请求参数：

```
{
    "account": 15570357290,
    "password": 123212321
}
```

返回数据：

```json
{
  "code": 200,
  "data": "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2Mzg3MDcxMzcsInVzZXJJZCI6MTU1NzAzNTcyOTAsImlhdCI6MTYzODYyMDczN30.s0LB0IKWlZAGUk1D3dxmdqzkO4Xt4N2T4R5eAIhReho",
  "msg": null
}
```



### 普通用户注册

接口url：localhost:9000/register/user

请求方式： post

请求参数：

```json
{
  "id": 15570357290,
  "username": "冰箱的主人",
  "password": 123212321,
  "mail": "Hyperion_LR@foxmail.com"
}
```

返回数据：

```json
{
  "code": 200,
  "data": "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2Mzg3MDcxMzcsInVzZXJJZCI6MTU1NzAzNTcyOTAsImlhdCI6MTYzODYyMDczN30.s0LB0IKWlZAGUk1D3dxmdqzkO4Xt4N2T4R5eAIhReho",
  "msg": null
}
```



### 发送验证码

接口url：localhost:9000/vercode

请求方式：post

请求参数

```json
{
  "mail": "Hyperion_LR@foxmail.com",
  "subject": "注册账号"
}
```

返回数据：

```json
{
  "code": 200,
  "data": null,
  "msg": null
}
```



### 上传文件

接口url：localhost:9000/upload/image

请求方式：post

请求参数：

```json
file:本地文件
```

返回参数：

```json
{
    "code": 200,
    "data": "r3gq8bup7.hd-bkt.clouddn.com/41996348-7bff-475f-bc16-29390a585bde.jpg",
    "msg": null
}
```



### 更新用户数据

接口url：localhost:9000/update/user

请求方式：post

请求参数：

```json
{
  "id": 15570357290,
  "username": "冰箱的主人",
  "avatar": "...",
  "mail": "Hyperion_LR@foxmail.com"
}
```

返回参数：

```json
{
  "code": 200,
  "data": null,
  "msg": null
}
```



### 更新用户密码

接口url：localhost:9000/update/password

请求方式：post

请求参数：

```json
{
  "userMail": "Hyperion_LR@foxmail.com",
  "code": 1234,
  "password": "123212321"
}
```

返回参数：

```json
{
  "code": 200,
  "data": null,
  "msg": null
}
```



### 销毁账号

接口url：localhost:9000/destroy

请求方式：post

请求参数：

```json
{
  "account": 15570357290,
  "password": 123212321
}
```

返回参数

```json
{
  "code": 200,
  "data": null,
  "msg": null
}
```

## 管理员

**（该模块所有请求(除了登录)的请求头请携带"Admin-Token",同时权限等级不够会被拦截）**

### 管理员登录

接口url：localhost:9000/login/admin

请求方式： post

请求参数：

```
{
    "account": 1,
    "password": 123456
}
```

返回数据：

```json
{
    "code": 200,
    "data": "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MzkzMDgzNDksInVzZXJJZCI6MSwiaWF0IjoxNjM5MjIxOTQ5fQ.EmQ6gJXgavQORt4jy5LqsZN6oAFqYpFgyJ1nibrYTn0",
    "msg": null
}
```



###   添加管理员

接口url：localhost:9000/admin/add

请求方式： post

请求参数：

```
{
    "id": 1234,
    "name": "Hyperion",
    "password": 123456,
    "permissionLevel": 0
}
```

返回数据：

```json
{
    "code": 200,
    "data": {
        "id": 1234,
        "name": "Hyperion",
        "password": "083f3977344768987d0e1d315b358f90",
        "permissionLevel": 0,
        "salt": "HyperionOJ"
    },
    "msg": null
}
```



###   更新管理员信息

接口url：localhost:9000/admin/update

请求方式： post

请求参数：

```
{
    "id": 1234,
    "name": "Hyperion",
    "password": 123456,
    "permissionLevel": 1
}
```

返回数据：

```json
{
    "code": 200,
    "data": {
        "id": 1234,
        "name": "Hyperion",
        "password": "083f3977344768987d0e1d315b358f90",
        "permissionLevel": 1,
        "salt": "HyperionOJ"
    },
    "msg": null
}
```



###   删除管理员账号

接口url：localhost:9000/admin/delete

请求方式： post

请求参数：

```json
{
    "id": 1234,
    "name": "Hyperion"
}
```

返回数据：

```json
{
    "code": 200,
    "data": null,
    "msg": null
}
```



###   冻结普通用户账号

接口url：localhost:9000/admin/freeze

请求方式： post

请求参数：

```json
{
    "id": 15570357290
}
```

返回数据：

```json
{
    "code": 200,
    "data": null,
    "msg": null
}
```



###   查询管理员行为

接口url：localhost:9000/admin/query/action

请求方式： post

请求参数：

```json
{
    "page": 1,
    "pageSize": 3,
    "adminId": 1,
    "action": "/add"
}
```

返回数据：

```json
{
    "code": 200,
    "data": [
        {
            "id": "1469629746873593858",
            "adminId": "1",
            "action": "/add",
            "time": "1639222010678",
            "status": 0
        },
        {
            "id": "1469495868724826114",
            "adminId": "1",
            "action": "/add",
            "time": "1639190091679",
            "status": 1
        },
        {
            "id": "1469495858423615489",
            "adminId": "1",
            "action": "/add",
            "time": "1639190089124",
            "status": 0
        }
    ],
    "msg": null
}
```



### 增加题库分类

接口url：localhost:9000/admin/add/problem/category

请求方式： post

请求参数：

```json
{
    "categoryName": "testCateGory",
    "description": "这个是测试分类1"
}
```

返回参数：

```json
{
    "code": 200,
    "data": {
        "id": "1470387437352689666",
        "categoryName": "testCateGory",
        "description": "这个是测试分类1"
    },
    "msg": null
}
```



### 删除题库分类

接口url：localhost:9000/admin/delete/problem/category

请求方式： delete

请求参数：

```json
{
    "id": "1470387894615711745"
}
```

返回参数：

```json
{
    "code": 200,
    "data": null,
    "msg": null
}
```



### 向题库添加题目（暂时，以后会更改）

接口url：localhost:9000/admin/add/problem

请求方式： post

请求参数：

```json
{
    "title": "test",
    "problemLevel": 1,
    "categoryId": "1",
    "caseNumber": 1,
    "runMemory": 256,
    "runTime": 1000,
    "problemBodyVo": {
        "problemBody": "这是测试",
        "problemBodyHtml": "<p>这是测试</p>"
    }
}
```

返回参数：

```json
{
    "code": 200,
    "data": {
        "id": "1470389250931982337",
        "title": "test",
        "bodyId": "1470389250806153218",
        "problemLevel": 1,
        "categoryId": "1",
        "acNumber": 0,
        "submitNumber": 0,
        "solutionNumber": 0,
        "commentNumber": 0,
        "caseNumber": 1,
        "runMemory": 256,
        "runTime": 1000,
        "problemBodyVo": {
            "id": null,
            "problemBody": "这是测试",
            "problemBodyHtml": "<p>这是测试</p>"
        }
    },
    "msg": null
}
```





### 更新题目

接口url：localhost:9000/admin/update/problem

请求方式： post

请求参数：

```json
{
    "id":"1470389250931982337",
    "title": "test",
    "bodyId": "1470389250806153218",
    "problemLevel": 1,
    "categoryId": "1",
    "caseNumber": 1,
    "runMemory": 256,
    "runTime": 2000,
    "problemBodyVo": {
        "problemBody": "这是update测试",
        "problemBodyHtml": "<p>这是update测试</p>"
    }
}
```

返回参数：

```json
{
    "code": 200,
    "data": null,
    "msg": null
}
```



### 删除题目

接口url：localhost:9000/admin/delete/problem

请求方式：delete

请求参数：

```json
{
    "id":"1470389250931982337",
    "bodyId": "1470389250806153218",
}
```

返回参数：

```json
{
    "code": 200,
    "data": null,
    "msg": null
}
```



### 删除用户评论

接口url：localhost:9000/problem/admin/delete/comment

请求方式：post

请求参数：

```json
{
    "id": "1469944007879221250"
}
```

返回参数：

```json
{
    "code": 200,
    "data": null,
    "msg": null
}
```



### 删除文章

接口url：localhost:9000/article/admin/delete/article

请求方式：post

请求参数：

```json
{
    "id": "1471319518832435201"
}
```

返回参数：

```json
{
    "code": 200,
    "data": null,
    "msg": null
}
```



### 删除文章评论

接口url：localhost:9000/article/admin/delete/article

请求方式：post

请求参数：

```json
{
    "id": "1471319518832435201"
}
```

返回参数：

```json
{
    "code": 200,
    "data": null,
    "msg": null
}
```



## 题库模块

### 获取题目列表（分页查询）

接口url：localhost:9000/problem/list

请求方式：get

请求参数：

```json
{
    "page": 1, 			// 页号
    "pageSize": 10,		// 页大小
    "level": null, 		// 题目难度
    "categoryId": null	// 分类id
}
```

返回参数

```json
{
    "code": 200,
    "data": [
        {
            "id": "1",				// 题目id
            "title": "test", 		// 题目标题
            "bodyId": "1",			// 题目详细id
            "problemLevel": 1,		// 题目难度1:简单， 2:中等，3:困难
            "acNumber": 1,			// 通过数量
            "submitNumber": 1,		// 提交数量
            "solutionNumber": 0,	// 题解数量
            "commentNumber": 1,		// 评论数量
            "caseNumber": null,		// 测试点数量，一般返回null
            "runMemory": 256,		// 运行内存限制
            "runTime": 1000,		// 运行时间限制
            "problemBodyVo": null,	// 题目详情
            "category": {			// 分类
                "id": "1",
                "categoryName": "test",
                "description": "测试分类"
            },
            "tags": [				// 标签
                {
                    "id": "1",
                    "tagName": "测试标签"
                },
                {
                    "id": "2",
                    "tagName": "test2"
                }
            ]
        },
        {
            "id": "2",
            "title": "test2",
            "bodyId": "2",
            "problemLevel": 1,
            "acNumber": 0,
            "submitNumber": 0,
            "solutionNumber": 0,
            "commentNumber": 0,
            "caseNumber": null,
            "runMemory": 256,
            "runTime": 1000,
            "problemBodyVo": null,
            "category": {
                "id": "1",
                "categoryName": "test",
                "description": "测试分类"
            },
            "tags": [
                {
                    "id": "2",
                    "tagName": "test2"
                }
            ]
        }
    ],
    "msg": null
}
```



### 通过id查看题目

接口url：localhost:9000/problem/{id}

请求方式：get

请求参数：

```json
{
}
```

返回参数

```json
{
    "code": 200,
    "data": {
        "commentNumber": 1,							// 题目详细信息
        "problemBodyVo": {
            "problemBodyHtml": "<p>这是测试</p>"		// 文章体
        },
        "problemLevel": 1,							// 题目难度
        "title": "test",
        "solutionNumber": 0,						
        "tags": [
            {
                "id": "1",
                "tagName": "测试标签"
            },
            {
                "id": "2",
                "tagName": "test2"
            }
        ],
        "acNumber": 1,
        "runMemory": 256,
        "caseNumber": 1,
        "bodyId": "1",
        "id": "1",
        "runTime": 1000,
        "category": {
            "description": "测试分类",
            "id": "1",
            "categoryName": "test"
        },
        "submitNumber": 1
    },
    "msg": null
}
```



### 提交题目

接口url：localhost:9000/problem/{id}

请求方式：get

请求参数：

```json
{
    "authorId": 15570357290,
    "problemId": 1,
    "codeLang": "java",
    "codeBody": "import java.util.Scanner;\n/**\n * @author Hyperion\n * @date 2021/11/28\n */\npublic class Main {\npublic static void main(String[] args) {\nString name = \"Hyperion\";\nScanner in = new Scanner(System.in);\n int[] abc = new int[10000000];\nint t = 0, a = 0, b = 0;\nSystem.out.println(name);\nt = in.nextInt();\nwhile(t-- > 0){\na = in.nextInt();\nb = in.nextInt();\nSystem.out.println(a + b);\n}\nSystem.out.println(name);\n}\n}",
    "runTime": 1000,
    "runMemory": 256,
    "caseNumber": 1
}
```

返回参数

```json
{
    "code": 200,
    "data": {
        "authorId": "15570357290",
        "submitId": null,
        "problemId": 1,
        "verdict": "AC",
        "msg": "Hyperion\n3\n5\n5\nHyperion\n",
        "runTime": 137,
        "runMemory": 0,
        "submitTime": "2021-12-18 16:47"
    },
    "msg": null
}
```



### 获取所有题目分类

接口url：localhost:9000/problem/category

请求方式：get

请求参数

```json

```

返回参数

```json
{
    "code": 200,
    "data": [
        {
            "id": "1",
            "categoryName": "test",
            "description": "测试分类"
        },
        {
            "id": "2",
            "categoryName": "test2",
            "description": "测试分类2"
        }
    ],
    "msg": null
}
```



### 获取提交列表（分页查询）

接口url：localhost:9000/problem/submits

请求方式：get

请求参数：

```
{
    "page": 1,
    "pageSize": 3,
    "problemId": null,
    "username": null,
    "verdict": null, 	// AC,WA,PE,CE,RE，TLE,MLE这几个表示通过，答案错误，格式错误，编译错误，运行时错误,超时超内存
    "codeLang": null	// 目前只有java,c++,python
}
```

返回数据：

```json
{
    "code": 200,
    "data": [
        {
            "id": "1472126314742784001",
            "authorId": "15570357290",
            "problemId": "1",
            "codeLang": "java",
            "codeBody": null,
            "runTime": 137,
            "runMemory": 0,
            "caseNumber": null,
            "verdict": "AC",
            "createTime": "2021-12-18 16:47"
        },
        {
            "id": "1472113584614805506",
            "authorId": "15570357290",
            "problemId": "1",
            "codeLang": "java",
            "codeBody": null,
            "runTime": 129,
            "runMemory": 0,
            "caseNumber": null,
            "verdict": "AC",
            "createTime": "2021-12-18 15:56"
        },
        {
            "id": "1469934295938920449",
            "authorId": "15570357290",
            "problemId": "1",
            "codeLang": "java",
            "codeBody": null,
            "runTime": 151,
            "runMemory": 0,
            "caseNumber": null,
            "verdict": "AC",
            "createTime": "2021-12-12 15:37"
        },
        {
            "id": "1469934165613506561",
            "authorId": "15570357290",
            "problemId": "1",
            "codeLang": "java",
            "codeBody": null,
            "runTime": 0,
            "runMemory": 0,
            "caseNumber": null,
            "verdict": "CE",
            "createTime": "2021-12-12 15:36"
        },
        {
            "id": "1469934088903880705",
            "authorId": "15570357290",
            "problemId": "1",
            "codeLang": "java",
            "codeBody": null,
            "runTime": 0,
            "runMemory": 0,
            "caseNumber": null,
            "verdict": "CE",
            "createTime": "2021-12-12 15:36"
        },
        {
            "id": "1469933996805353473",
            "authorId": "15570357290",
            "problemId": "1",
            "codeLang": "java",
            "codeBody": null,
            "runTime": 126,
            "runMemory": 0,
            "caseNumber": null,
            "verdict": "AC",
            "createTime": "2021-12-12 15:35"
        },
        {
            "id": "1469933926999552002",
            "authorId": "15570357290",
            "problemId": "1",
            "codeLang": "java",
            "codeBody": null,
            "runTime": 120,
            "runMemory": 0,
            "caseNumber": null,
            "verdict": "AC",
            "createTime": "2021-12-12 15:35"
        },
        {
            "id": "1469932994488332290",
            "authorId": "15570357290",
            "problemId": "1",
            "codeLang": "java",
            "codeBody": null,
            "runTime": 116,
            "runMemory": 0,
            "caseNumber": null,
            "verdict": "WA",
            "createTime": "2021-12-12 15:31"
        },
        {
            "id": "1469932890868051970",
            "authorId": "15570357290",
            "problemId": "1",
            "codeLang": "java",
            "codeBody": null,
            "runTime": 134,
            "runMemory": 0,
            "caseNumber": null,
            "verdict": "WA",
            "createTime": "2021-12-12 15:31"
        },
        {
            "id": "1469932688937480194",
            "authorId": "15570357290",
            "problemId": "1",
            "codeLang": "java",
            "codeBody": null,
            "runTime": 126,
            "runMemory": 0,
            "caseNumber": null,
            "verdict": "AC",
            "createTime": "2021-12-12 15:30"
        }
    ],
    "msg": null
}
```



### 获取单次提交

接口url：localhost:9000/problem/submit/{id}

请求方式：get

请求参数：

```json

```

返回数据：

```json
{
    "code": 200,
    "data": {
        "id": "1469933996805353473",
        "authorId": "15570357290",
        "problemId": "1",
        "codeLang": "java",
        "codeBody": "import java.util.Scanner;\n/**\n * @author Hyperion\n * @date 2021/11/28\n */\npublic class Main {\npublic static void main(String[] args) {\nString name = \"Hyperion\";\nScanner in = new Scanner(System.in);\n int[] abc = new int[10000000];\nint t = 0, a = 0, b = 0;\nSystem.out.println(name);\nt = in.nextInt();\nwhile(t-- > 0){\na = in.nextInt();\nb = in.nextInt();\nSystem.out.println(a + b);\n}\nSystem.out.println(name)\n}\n}",
        "runTime": 126,
        "runMemory": 0,
        "caseNumber": null,
        "verdict": "AC",
        "createTime": "2021-12-12 15:35"
    },
    "msg": null
}
```



### 题目评论

接口url：localhost:9000/problem/comment

请求方式： post

请求参数：

```json
{
    "content": "评论测试3",
    "problemId": "1",
    "authorVo": {
        "id": "15570357290",
        "username": null,
        "mail": null
    },
    "parentId": null,
    "toUser": {
        "id": "15570357290",
        "username": null,
        "mail": null
    },
    "level": null
}
```

返回数据：

```json
{
    "code": 200,
    "data": {
        "id": "1471436141732007937",
        "content": "评论测试3",
        "problemId": "1",
        "authorVo": {
            "id": "15570357290",
            "username": null,
            "mail": null
        },
        "parentId": null,
        "toUser": {
            "id": "15570357290",
            "username": null,
            "mail": null
        },
        "supportNumber": null,
        "createDate": null,
        "level": null
    },
    "msg": null
}
```



### 给评论点赞

接口url：localhost:9000/problem/support/comment

请求方式：post

请求参数：

```json
{
    "id": "1470582267798310913"
}
```

返回参数：

```json
{
    "code": 200,
    "data": 7,		// 当前赞数
    "msg": null
}
```



### 获取评论列表(分页查询)

接口urllocalhost:9000/problem/comments

请求方式： get

请求参数：

```json
{
    "page": 1,
    "pageSize": 3,
    "problemId": "1"
}
```

返回数据：

```json
{
    "code": 200,
    "data": [
        {
            "id": "1471436141732007937",
            "content": "评论测试3",
            "problemId": "1",
            "authorVo": {
                "id": "15570357290",
                "username": "冰箱的主人",
                "mail": "Hyperion_LR@foxmail.com",
                "studentNumber": null
            },
            "parentId": "0",
            "toUser": null,
            "supportNumber": 0,
            "createDate": "2021-12-16 19:04",
            "level": 0
        },
        {
            "id": "1471322339334123521",
            "content": "评论测试3",
            "problemId": "1",
            "authorVo": {
                "id": "15570357290",
                "username": "冰箱的主人",
                "mail": "Hyperion_LR@foxmail.com",
                "studentNumber": null
            },
            "parentId": "0",
            "toUser": {
                "id": "15570357290",
                "username": "冰箱的主人",
                "mail": "Hyperion_LR@foxmail.com",
                "studentNumber": null
            },
            "supportNumber": 0,
            "createDate": "2021-12-16 11:32",
            "level": 0
        },
        {
            "id": "1471040527894482946",
            "content": "评论测试3",
            "problemId": "1",
            "authorVo": {
                "id": "15570357290",
                "username": "冰箱的主人",
                "mail": "Hyperion_LR@foxmail.com",
                "studentNumber": null
            },
            "parentId": "0",
            "toUser": {
                "id": "15570357290",
                "username": "冰箱的主人",
                "mail": "Hyperion_LR@foxmail.com",
                "studentNumber": null
            },
            "supportNumber": 0,
            "createDate": "2021-12-15 16:52",
            "level": 0
        }
    ],
    "msg": null
}
```



## 文章模块

### 获取文章列表

接口url：localhost:9000/article/articles

请求方式：get

请求参数：

```json
{
    "page": 1,
    "pageSize": 2,
    "categoryId": 1,			//分类id	备注的只是示例
    "tagId": 1,					// 标签id
    "problemId": 1,				// 题目id
    "username": "冰箱的主人", 	//	作者id
    "year": "2021",   			// 发布年
    "month": "1" 				// 发布月
}
```

返回参数：

```json
{
    "code": 200,
    "data": [
        {
            "id": "1471321831475212290",
            "title": "test title",
            "authorName": null,
            "authorId": null,
            "summary": "test",
            "createDate": "2021-12-16 11:30",
            "commentCounts": null,
            "viewCounts": null,
            "weight": 0,
            "body": null,
            "tags": [],
            "category": {
                "id": null,
                "categoryName": null,
                "description": null
            }
        },
        {
            "id": "1471319518832435201",
            "title": "test title",
            "authorName": null,
            "authorId": null,
            "summary": "test",
            "createDate": "2021-12-16 11:21",
            "commentCounts": null,
            "viewCounts": null,
            "weight": 0,
            "body": null,
            "tags": [],
            "category": {
                "id": null,
                "categoryName": null,
                "description": null
            }
        },
        {
            "id": "1",
            "title": "测试文章",
            "authorName": null,
            "authorId": null,
            "summary": "这是...",
            "createDate": "1970-01-01 08:00",
            "commentCounts": null,
            "viewCounts": null,
            "weight": 0,
            "body": null,
            "tags": [],
            "category": {
                "id": null,
                "categoryName": null,
                "description": null
            }
        }
    ],
    "msg": null
}
```



### 获取最新的文章

接口url：localhost:9000/article/new

请求方式：get

请求参数：

```json
{
    "pageSize": 6
}
```

返回参数：

```json
{
    "code": 200,
    "data": [
        {
            "id": "1471321831475212290",
            "title": "test title",
            "authorName": null,
            "authorId": null,
            "summary": null,
            "createDate": null,
            "commentCounts": null,
            "viewCounts": null,
            "weight": null,
            "body": null,
            "tags": null,
            "category": null
        },
        {
            "id": "1471319518832435201",
            "title": "test title",
            "authorName": null,
            "authorId": null,
            "summary": null,
            "createDate": null,
            "commentCounts": null,
            "viewCounts": null,
            "weight": null,
            "body": null,
            "tags": null,
            "category": null
        },
        {
            "id": "1",
            "title": "测试文章",
            "authorName": null,
            "authorId": null,
            "summary": null,
            "createDate": null,
            "commentCounts": null,
            "viewCounts": null,
            "weight": null,
            "body": null,
            "tags": null,
            "category": null
        }
    ],
    "msg": null
}
```



### 近期热门文章

接口url：localhost:9000/article/hot

请求方式：get

请求参数：

```json
{
    "pageSize": 6
}
```

返回参数：

```json
{
    "code": 200,
    "data": [
        {
            "id": "1",
            "title": "测试文章",
            "authorName": null,
            "authorId": null,
            "summary": null,
            "createDate": null,
            "commentCounts": null,
            "viewCounts": null,
            "weight": null,
            "body": null,
            "tags": null,
            "category": null
        },
        {
            "id": "1471319518832435201",
            "title": "test title",
            "authorName": null,
            "authorId": null,
            "summary": null,
            "createDate": null,
            "commentCounts": null,
            "viewCounts": null,
            "weight": null,
            "body": null,
            "tags": null,
            "category": null
        },
        {
            "id": "1471321831475212290",
            "title": "test title",
            "authorName": null,
            "authorId": null,
            "summary": null,
            "createDate": null,
            "commentCounts": null,
            "viewCounts": null,
            "weight": null,
            "body": null,
            "tags": null,
            "category": null
        }
    ],
    "msg": null
}
```



### 获取文章归档

接口url： localhost:9000/article/listArchives

请求方式：get

请求参数：

```json

```

返回参数：

```json
{
    "code": 200,
    "data": [
        {
            "year": 1970,
            "month": 1,
            "count": 1
        },
        {
            "year": 2021,
            "month": 12,
            "count": 2
        }
    ],
    "msg": null
}
```



### 查看文章详情

接口url： localhost:9000/article/view/1

请求方式：get

请求参数：

```json

```

返回参数：

```json
{
    "code": 200,
    "data": {
        "id": "1",
        "title": "测试文章",
        "author": {
            "id": "15570357290",
            "username": "冰箱的主人",
            "mail": "Hyperion_LR@foxmail.com"
        },
        "summary": "这是...",
        "createDate": "1970-01-01 08:00",
        "commentCounts": 1,
        "viewCounts": 0,
        "weight": 0,
        "body": {
            "content": "这是测试文字"
        },
        "tags": [
            {
                "id": "1",
                "tagName": "测试标签"
            }
        ],
        "category": {
            "id": "1",
            "categoryName": "test",
            "description": "测试分类"
        }
    },
    "msg": null
}
```



### 发布文章

接口url： localhost:9000/article/publish

请求方式：post

请求参数：

```json
{
    "title": "test title",
    "body": {
        "content": "这是一段测试文字",
        "contentHtml": "<p>这是一段测试文字</p>"
    },
    "category": {
        "id": 1,
        "categoryName": "test",
        "description": "测试专用"
    },
    "summary": "test",
    "tags": [
        {
            "id": 1,
            "tagName": "test"
        }
    ],
    "isSolution": "0",
    "problemId": "1"
}
```

返回参数：

```json
{
    "code": 200,
    "data": {
        "id": "1471453432829939714",
        "title": "test title",
        "author": {
            "id": "15570357290",
            "username": "冰箱的主人",
            "mail": "Hyperion_LR@foxmail.com"
        },
        "summary": "test",
        "createDate": "2021-12-16 20:13",
        "commentCounts": 0,
        "viewCounts": 0,
        "weight": 0,
        "body": {
            "content": "这是一段测试文字"
        },
        "tags": [
            {
                "id": "1",
                "tagName": "测试标签"
            }
        ],
        "category": {
            "id": "1",
            "categoryName": "test",
            "description": "测试分类"
        }
    },
    "msg": null
}
```



### 热门标签

接口url： localhost:9000/article/tag/hot

请求方式：get

请求参数：

```json

```

返回参数：

```json
{
    "code": 200,
    "data": [
        {
            "id": 1,
            "tagName": null
        }
    ],
    "msg": null
}
```



### 获取标签

接口url： localhost:9000/article/category/detail

请求方式：get

请求参数：

```json

```

返回参数：

```json
{
    "code": 200,
    "data": [
        {
            "id": 1,
            "categoryName": "test",
            "description": "测试分类"
        }
    ],
    "msg": null
}
```



### 标签详情

接口url：

请求方式：get

请求参数： localhost:9000/article/category/detail/1

```json

```

返回参数：

```json
{
    "code": 200,
    "data": {
        "id": "1",
        "categoryName": "test",
        "description": "测试分类"
    },
    "msg": null
}
```



### 获取文章下评论

接口url：localhost:9000/article/comments/article/1

请求方式：get

请求参数：

```json

```

返回参数：

```json
{
    "code": 200,
    "data": [
        {
            "id": "1471441486713868289",
            "content": "测试内容",
            "problemId": null,
            "authorVo": null,
            "parentId": null,
            "toUser": null,
            "supportNumber": null,
            "createDate": "2021-12-16 19:26:03",
            "level": 1
        }
    ],
    "msg": null
}
```



### 提交评论

接口url：localhost:9000/article/comments/comment

请求方式：post

请求参数：

```json
{
    "articleId": "1",
    "content": "测试内容",
    "parent": null,				// 是哪个评论下的子评论
    "toUserId": "15570357290"
}
```

返回参数：

```json
{
    "code": 200,
    "data": {
        "id": "1471459657550290946",
        "content": "测试内容",
        "problemId": null,
        "authorVo": {
            "id": "15570357290",
            "username": "冰箱的主人",
            "mail": "Hyperion_LR@foxmail.com"
        },
        "parentId": "0",
        "toUser": null,
        "supportNumber": 0,
        "createDate": "2021-12-16 20:38:15",
        "level": 1
    },
    "msg": null
}
```



### 删除文章

接口url：localhost:9000/article/delete/article

请求方式：post

请求参数：

```json
{
    "id": "1471319518832435201"
}
```

返回参数：

```json
{
    "code": 200,
    "data": null,
    "msg": null
}
```



### 删除文章评论

接口url：localhost:9000/article/delete/article

请求方式：post

请求参数：

```json
{
    "id": "1471319518832435201"
}
```

返回参数：

```json
{
    "code": 200,
    "data": null,
    "msg": null
}
```



## 班级管理

此模块下的请求请携带token

### 学生端

### 查看所有课程

接口url：localhost:9000/school/user/class

请求方式：get

请求参数：

```json

```

返回参数：

```json
{
    "code": 200,
    "data": [
        {
            "id": "1472029371140030465",
            "teacherId": "1",
            "teacherName": "Hyperion",
            "courseName": "测试课程",
            "academy": "测试学院",
            "students": null
        },
        {
            "id": "1472059833363439617",
            "teacherId": "1",
            "teacherName": "Hyperion",
            "courseName": "测试课程2",
            "academy": "测试学院",
            "students": null
        }
    ],
    "msg": null
}
```



### 查看班级具体情况

接口url：localhost:9000/school/user/class/{班级id}

请求方式：get

请求参数：

```json

```

返回参数：

```json
{
    "code": 200,
    "data": {
        "id": "1472029371140030465",
        "teacherId": "1",
        "teacherName": "Hyperion",
        "courseName": "测试课程",
        "academy": "测试学院",
        "students": [
            {
                "id": "15570357290",
                "username": "冰箱的主人",
                "mail": "Hyperion_LR@foxmail.com",
                "studentNumber": null
            }
        ]
    },
    "msg": null
}
```



### 查看课程所有作业

接口url：localhost:9000/school/user/homeworks/{课程id}

请求方式：get

请求参数：

```json

```

返回参数：

```json
{
    "code": 200,
    "data": [
        {
            "id": "1472041160938569730",
            "classId": "1",
            "name": "第一次测试作业",
            "startTime": "2021-12-18 12:00",
            "endTime": "2021-12-18 12:00",
            "teacherId": "1",
            "teacherName": "Hyperion",
            "problems": null,
            "submitVos": null
        },
        {
            "id": "1472041215334498305",
            "classId": "1",
            "name": "第二次测试作业",
            "startTime": "2021-12-18 12:00",
            "endTime": "2021-12-18 12:00",
            "teacherId": "1",
            "teacherName": "Hyperion",
            "problems": null,
            "submitVos": null
        },
        {
            "id": "1472041260456820738",
            "classId": "1",
            "name": "第二次测试作业",
            "startTime": "2021-12-18 12:00",
            "endTime": "2021-12-18 12:00",
            "teacherId": "1",
            "teacherName": "Hyperion",
            "problems": null,
            "submitVos": null
        },
        {
            "id": "1472041681653022722",
            "classId": "1",
            "name": "第二次测试作业",
            "startTime": "2021-12-18 12:00",
            "endTime": "2021-12-18 12:00",
            "teacherId": "1",
            "teacherName": "Hyperion",
            "problems": null,
            "submitVos": null
        },
        {
            "id": "1472041764129816578",
            "classId": "1",
            "name": "第二次测试作业",
            "startTime": "2021-12-18 12:00",
            "endTime": "2021-12-18 12:00",
            "teacherId": "1",
            "teacherName": "Hyperion",
            "problems": null,
            "submitVos": null
        }
    ],
    "msg": null
}
```



### 查看作业具体详情

接口url：localhost:9000/school/user/homework/{作业id}

请求方式：get

请求参数：

```json

```

返回参数：

```json
{
    "code": 200,
    "data": {
        "id": "1472041160938569730",
        "classId": "1",
        "name": "第一次测试作业",
        "startTime": "2021-12-18 12:00",
        "endTime": "2021-12-18 12:00",
        "teacherId": "1",
        "teacherName": "Hyperion",
        "problems": [
            {
                "id": "1",
                "title": "test",
                "bodyId": "1",
                "problemLevel": 1,
                "acNumber": 2,
                "submitNumber": 2,
                "solutionNumber": 0,
                "commentNumber": 1,
                "caseNumber": 1,
                "runMemory": 256,
                "runTime": 1000,
                "problemBodyVo": {
                    "id": null,
                    "problemBody": null,
                    "problemBodyHtml": "<p>这是测试</p>"
                },
                "category": {
                    "id": "1",
                    "categoryName": "test",
                    "description": "测试分类"
                },
                "tags": [
                    {
                        "id": "1",
                        "tagName": "测试标签"
                    },
                    {
                        "id": "2",
                        "tagName": "test2"
                    }
                ]
            },
            {
                "id": "2",
                "title": "test2",
                "bodyId": "2",
                "problemLevel": 1,
                "acNumber": 0,
                "submitNumber": 0,
                "solutionNumber": 0,
                "commentNumber": 0,
                "caseNumber": 1,
                "runMemory": 256,
                "runTime": 1000,
                "problemBodyVo": {
                    "id": null,
                    "problemBody": null,
                    "problemBodyHtml": "<p>这是测试</p>"
                },
                "category": {
                    "id": "1",
                    "categoryName": "test",
                    "description": "测试分类"
                },
                "tags": [
                    {
                        "id": "2",
                        "tagName": "test2"
                    }
                ]
            }
        ],
        "submitVos": []
    },
    "msg": null
}
```



### 加入课程

接口url：localhost:9000/school/user/class/join/{课程id}

请求方式：

请求参数：

```json

```

返回参数：

1. 班级详情

```json
{
    "code": 200,
    "data": {
        "id": "1472059833363439617",
        "teacherId": "1",
        "teacherName": "Hyperion",
        "courseName": "测试课程2",
        "academy": "测试学院",
        "students": [
            {
                "id": "15570357290",
                "username": "冰箱的主人",
                "mail": "Hyperion_LR@foxmail.com",
                "studentNumber": null
            }
        ]
    },
    "msg": null
}
```

2. 已经在班级里面了

```json
{
    "code": 200,
    "data": "已经在班级里了!",
    "msg": null
}
```



### 提交作业

接口url：localhost:9000/school/user/submit/1472041160938569730

请求方式：post

请求参数：

```json
{
    "authorId": 15570357290,
    "problemId": 1,
    "codeLang": "java",
    "codeBody": "import java.util.Scanner;\n/**\n * @author Hyperion\n * @date 2021/11/28\n */\npublic class Main {\npublic static void main(String[] args) {\nString name = \"Hyperion\";\nScanner in = new Scanner(System.in);\n int[] abc = new int[10000000];\nint t = 0, a = 0, b = 0;\nSystem.out.println(name);\nt = in.nextInt();\nwhile(t-- > 0){\na = in.nextInt();\nb = in.nextInt();\nSystem.out.println(a + b);\n}\nSystem.out.println(name);\n}\n}"
}
```

返回参数：

```json
{
    "code": 200,
    "data": {
        "authorId": "15570357290",
        "submitId": 1472394075930800130,
        "problemId": 1,
        "verdict": "AC",
        "msg": "Hyperion\n3\n5\n5\nHyperion\n",
        "runTime": 156,
        "runMemory": 0,
        "submitTime": "2021-12-19 10:31"
    },
    "msg": null
}
```



### 教师端

### 查看班级列表

接口url：localhost:9000/school/teacher/class

请求方式：get

请求参数：

```json

```

返回参数：

```json
{
    "code": 200,
    "data": [
        {
            "id": "1472029371140030465",
            "teacherId": "1",
            "teacherName": "Hyperion",
            "courseName": "测试课程",
            "academy": "测试学院",
            "students": null
        },
        {
            "id": "1472059833363439617",
            "teacherId": "1",
            "teacherName": "Hyperion",
            "courseName": "测试课程2",
            "academy": "测试学院",
            "students": null
        }
    ],
    "msg": null
}
```





### 查看班级

接口url：localhost:9000/school/class/1

请求方式：get

请求参数：

```json

```

返回参数：

```json
{
    "code": 200,
    "data": {
        "id": "1472029371140030465",
        "teacherId": "1",
        "teacherName": "Hyperion",
        "courseName": "测试课程",
        "academy": "测试学院",
        "students": [
            {
                "id": "15570357290",
                "username": "冰箱的主人",
                "mail": "Hyperion_LR@foxmail.com",
                "studentNumber": null
            }
        ]
    },
    "msg": null
}
```



### 创建班级

接口url：localhost:9000/school/create

请求方式：post

请求参数：

```json
{
    "teacherId": "1",
    "teacherName": "Hyperion",
    "courseName": "测试课程2",
    "academy": "测试学院"
}
```

返回参数：

```json
{
    "code": 200,
    "data": {
        "id": "1472394890775015425",
        "teacherId": "1",
        "teacherName": "Hyperion",
        "courseName": "测试课程2",
        "academy": "测试学院",
        "students": null
    },
    "msg": null
}
```



### 添加学生

接口url：localhost:9000/school/add/{课程id}

请求方式：post

请求参数：

```json
{
    "studentNumber": "1"
}
```

返回参数：

```json
{
    "code": 200,
    "data": null,
    "msg": null
}
```



### 移除学生

接口url：localhost:9000/school/remove/{课程id}

请求方式：post

请求参数：

```json
{
    "studentNumber": "1"
}
```

返回参数：

```json
{
    "code": 200,
    "data": null,
    "msg": null
}
```



### 发布作业

接口url：localhost:9000/school/teacher/push

请求方式：post

请求参数：

```json
{
    "classId": "1",
    "name": "第一次测试作业",
    "startDate": "2021-12-18 12:00",
    "endDate": "2021-12-18 12:00",
    "teacherId": "1",
    "problemVos": [
        {
            "id": 1
        },
        {
            "id": 2
        }
    ]
}
```

返回参数：

```json
{
    "code": 200,
    "data": {
        "id": "1472395994707439617",
        "classId": "1",
        "name": "第一次测试作业",
        "startTime": "2021-12-18 12:00",
        "endTime": "2021-12-18 12:00",
        "teacherId": "1",
        "teacherName": "Hyperion",
        "problems": [
            {
                "id": "1",
                "title": "test",
                "bodyId": "1",
                "problemLevel": 1,
                "acNumber": 5,
                "submitNumber": 5,
                "solutionNumber": 0,
                "commentNumber": 1,
                "caseNumber": 1,
                "runMemory": 256,
                "runTime": 1000,
                "problemBodyVo": {
                    "id": null,
                    "problemBody": null,
                    "problemBodyHtml": "<p>这是测试</p>"
                },
                "category": {
                    "id": "1",
                    "categoryName": "test",
                    "description": "测试分类"
                },
                "tags": [
                    {
                        "id": "1",
                        "tagName": "测试标签"
                    },
                    {
                        "id": "2",
                        "tagName": "test2"
                    }
                ]
            },
            {
                "id": "2",
                "title": "test2",
                "bodyId": "2",
                "problemLevel": 1,
                "acNumber": 0,
                "submitNumber": 0,
                "solutionNumber": 0,
                "commentNumber": 0,
                "caseNumber": 1,
                "runMemory": 256,
                "runTime": 1000,
                "problemBodyVo": {
                    "id": null,
                    "problemBody": null,
                    "problemBodyHtml": "<p>这是测试</p>"
                },
                "category": {
                    "id": "1",
                    "categoryName": "test",
                    "description": "测试分类"
                },
                "tags": [
                    {
                        "id": "2",
                        "tagName": "test2"
                    }
                ]
            }
        ],
        "submitVos": []
    },
    "msg": null
}
```



### 更新作业

接口url：localhost:9000/school/update/homework

请求方式：post

请求参数：

```json
{
    "id": "1472039743838437378",
    "classId": "1",
    "name": "第二次测试作业",
    "startDate": "2021-12-18 12:00",
    "endDate": "2021-12-18 12:00",
    "teacherId": "1",
    "problemVos": [
        {
            "id": 1
        },
                {
            "id": 2
        }
    ]
}
```

返回参数：

```json
{
    "code": 200,
    "data": {
        "id": "1472395994707439617",
        "classId": "1",
        "name": "第二次测试作业",
        "startTime": "2021-12-18 12:00",
        "endTime": "2021-12-18 12:00",
        "teacherId": "1",
        "teacherName": "Hyperion",
        "problems": [
            {
                "id": "1",
                "title": "test",
                "bodyId": "1",
                "problemLevel": 1,
                "acNumber": 5,
                "submitNumber": 5,
                "solutionNumber": 0,
                "commentNumber": 1,
                "caseNumber": 1,
                "runMemory": 256,
                "runTime": 1000,
                "problemBodyVo": {
                    "id": null,
                    "problemBody": null,
                    "problemBodyHtml": "<p>这是测试</p>"
                },
                "category": {
                    "id": "1",
                    "categoryName": "test",
                    "description": "测试分类"
                },
                "tags": [
                    {
                        "id": "1",
                        "tagName": "测试标签"
                    },
                    {
                        "id": "2",
                        "tagName": "test2"
                    }
                ]
            },
            {
                "id": "2",
                "title": "test2",
                "bodyId": "2",
                "problemLevel": 1,
                "acNumber": 0,
                "submitNumber": 0,
                "solutionNumber": 0,
                "commentNumber": 0,
                "caseNumber": 1,
                "runMemory": 256,
                "runTime": 1000,
                "problemBodyVo": {
                    "id": null,
                    "problemBody": null,
                    "problemBodyHtml": "<p>这是测试</p>"
                },
                "category": {
                    "id": "1",
                    "categoryName": "test",
                    "description": "测试分类"
                },
                "tags": [
                    {
                        "id": "2",
                        "tagName": "test2"
                    }
                ]
            }
        ],
        "submitVos": []
    },
    "msg": null
}
```



### 删除作业

接口url：localhost:9000/school/delete/homework

请求方式：delete

请求参数：

```json
{
    "id": "1472039748997431297"
}
```

返回参数：

```json
{
    "code": 200,
    "data": [
        {
            "id": "1472041160938569730",
            "classId": "1",
            "name": "第一次测试作业",
            "startTime": "2021-12-18 12:00",
            "endTime": "2021-12-18 12:00",
            "teacherId": "1",
            "teacherName": "Hyperion",
            "problems": null,
            "submitVos": null
        },
        {
            "id": "1472041215334498305",
            "classId": "1",
            "name": "第二次测试作业",
            "startTime": "2021-12-18 12:00",
            "endTime": "2021-12-18 12:00",
            "teacherId": "1",
            "teacherName": "Hyperion",
            "problems": null,
            "submitVos": null
        },
        {
            "id": "1472041260456820738",
            "classId": "1",
            "name": "第二次测试作业",
            "startTime": "2021-12-18 12:00",
            "endTime": "2021-12-18 12:00",
            "teacherId": "1",
            "teacherName": "Hyperion",
            "problems": null,
            "submitVos": null
        },
        {
            "id": "1472041681653022722",
            "classId": "1",
            "name": "第二次测试作业",
            "startTime": "2021-12-18 12:00",
            "endTime": "2021-12-18 12:00",
            "teacherId": "1",
            "teacherName": "Hyperion",
            "problems": null,
            "submitVos": null
        },
        {
            "id": "1472041764129816578",
            "classId": "1",
            "name": "第二次测试作业",
            "startTime": "2021-12-18 12:00",
            "endTime": "2021-12-18 12:00",
            "teacherId": "1",
            "teacherName": "Hyperion",
            "problems": null,
            "submitVos": null
        }
    ],
    "msg": null
}
```



### 获取作业列表

接口url：localhost:9000/school/teacher/homeworks/{课程id}

请求方式：get

请求参数：

```json

```

返回参数：

```json
{
    "code": 200,
    "data": [
        {
            "id": "1472041160938569730",
            "classId": "1472029371140030465",
            "name": "第一次测试作业",
            "startTime": "2021-12-18 12:00",
            "endTime": "2021-12-18 12:00",
            "teacherId": "1",
            "teacherName": "Hyperion",
            "problems": null,
            "submitVos": null
        },
        {
            "id": "1472041215334498305",
            "classId": "1472029371140030465",
            "name": "第二次测试作业",
            "startTime": "2021-12-18 12:00",
            "endTime": "2021-12-18 12:00",
            "teacherId": "1",
            "teacherName": "Hyperion",
            "problems": null,
            "submitVos": null
        },
        {
            "id": "1472041260456820738",
            "classId": "1472029371140030465",
            "name": "第二次测试作业",
            "startTime": "2021-12-18 12:00",
            "endTime": "2021-12-18 12:00",
            "teacherId": "1",
            "teacherName": "Hyperion",
            "problems": null,
            "submitVos": null
        }
    ],
    "msg": null
}
```



### 获取作业情况

接口url：localhost:9000/school/homework/1472039743838437378

请求方式：get

请求参数：

```json

```

返回参数：

```json
{
    "code": 200,
    "data": {
        "id": "1472041160938569730",
        "classId": "1472029371140030465",
        "name": "第一次测试作业",
        "startTime": "2021-12-18 12:00",
        "endTime": "2021-12-18 12:00",
        "teacherId": "1",
        "teacherName": "Hyperion",
        "problems": [
            {
                "id": "1",
                "title": "test",
                "bodyId": "1",
                "problemLevel": 1,
                "acNumber": 5,
                "submitNumber": 5,
                "solutionNumber": 0,
                "commentNumber": 1,
                "caseNumber": 1,
                "runMemory": 256,
                "runTime": 1000,
                "problemBodyVo": {
                    "id": null,
                    "problemBody": null,
                    "problemBodyHtml": "<p>这是测试</p>"
                },
                "category": {
                    "id": "1",
                    "categoryName": "test",
                    "description": "测试分类"
                },
                "tags": [
                    {
                        "id": "1",
                        "tagName": "测试标签"
                    },
                    {
                        "id": "2",
                        "tagName": "test2"
                    }
                ]
            },
            {
                "id": "2",
                "title": "test2",
                "bodyId": "2",
                "problemLevel": 1,
                "acNumber": 0,
                "submitNumber": 0,
                "solutionNumber": 0,
                "commentNumber": 0,
                "caseNumber": 1,
                "runMemory": 256,
                "runTime": 1000,
                "problemBodyVo": {
                    "id": null,
                    "problemBody": null,
                    "problemBodyHtml": "<p>这是测试</p>"
                },
                "category": {
                    "id": "1",
                    "categoryName": "test",
                    "description": "测试分类"
                },
                "tags": [
                    {
                        "id": "2",
                        "tagName": "test2"
                    }
                ]
            }
        ],
        "submitVos": null
    },
    "msg": null
}
```



### 获取提交情况

接口url：localhost:9000/school/submits/{作业id}

请求方式：get

请求参数：

```json

```

返回参数：

```json
{
    "code": 200,
    "data": [
        {
            "id": "1472394075930800130",
            "authorId": "15570357290",
            "problemId": "1",
            "codeLang": "java",
            "codeBody": "import java.util.Scanner;\n/**\n * @author Hyperion\n * @date 2021/11/28\n */\npublic class Main {\npublic static void main(String[] args) {\nString name = \"Hyperion\";\nScanner in = new Scanner(System.in);\n int[] abc = new int[10000000];\nint t = 0, a = 0, b = 0;\nSystem.out.println(name);\nt = in.nextInt();\nwhile(t-- > 0){\na = in.nextInt();\nb = in.nextInt();\nSystem.out.println(a + b);\n}\nSystem.out.println(name);\n}\n}",
            "runTime": 156,
            "runMemory": 0,
            "caseNumber": null,
            "verdict": "AC",
            "createTime": "2021-12-19 10:31"
        }
    ],
    "msg": null
}
```



### 需求

接口url：

请求方式：

请求参数：

```json

```

返回参数：

```json

```



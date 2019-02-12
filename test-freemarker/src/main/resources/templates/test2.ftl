<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>hello freemarker</title>
</head>
<body>
遍历list数据
<table>
    <tr>
        <td>序号</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>钱包</td>
    </tr>
    <#list stus as student>
        <tr>
        <td>${student_index + 1}</td>
        <td>${student.name}</td>
        <td>${student.age}</td>
        <td>${student.money}</td>
        </tr>
    </#list>
</table>
<hr>
遍历map数据
姓名:${stuMap.stu1.name}
    年龄:${stuMap.stu1.age}
    </br>
    姓名:${stuMap['stu1'].name}
    年龄:${stuMap['stu1'].age}
<hr>
遍历输出两个学生信息
<table>
    <tr>
        <td>序号</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>钱包</td>
    </tr>
    <#list stuMap?keys as key>

        <tr>
        <td>${key_index + 1}</td>
        <td>${stuMap[key].name}</td>
        <td>${stuMap[key].age}</td>
        <td>${stuMap[key].money}</td>
        </tr>
    </#list>
</table>
<hr>
测试if指令
<table>
    <tr>
        <td>序号</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>钱包</td>
    </tr>
    <#list stuMap?keys as key>

        <tr>
        <td>${key_index + 1}</td>
    <td <#if stuMap[key].name == '小明'>style="background: aqua;" </#if>>${stuMap[key].name}</td>
        <td>${stuMap[key].age}</td>
    <td
    <#if (stuMap[key].money>100 && stuMap[key].money<1000)>style="background: brown;" </#if>>${stuMap[key].money}</td>
        </tr>
    </#list>
</table>

<hr>
<table>
    <tr>
        <td>序号</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>钱包</td>
        <td>最好的朋友</td>
        <td>朋友个数</td>
        <td>朋友列表</td>
    </tr>
    <#if stus?? >
        <#list stus as key>

            <tr>
            <td>${key_index + 1}</td>
            <td>${key.name}</td>
            <td>${key.age}</td>
            <td>${key.money}</td>
            <td>${(key.bestFriend.name)!''}</td>
            <td>${(key.friends?size)!0}</td>
            <td>${key.money}</td>
            </tr>
        </#list>
    </#if>
</table>

</body>
</html>
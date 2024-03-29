# MyBatis

## mybatis # 和 $ 的区别
在 **MyBatis** 中，**#{}** 和 **${}** 是两种不同的参数占位符，用于在 SQL 语句中引用形参变量。让我详细解释一下它们的区别和适用场景：

1. **#{} 方式**：
   - **#{}** 是一种预编译方式，会将形参变量的值取出，并自动添加引号。
   - 例如，当实参 `username` 的值是 `"Amy"` 时，传入以下 Mapper 映射文件：
     ```xml
     <select id="findByName" parameterType="String" resultMap="studentResultMap">
         SELECT * FROM user WHERE username=#{value}
     </select>
     ```
     SQL 将解析为：
     ```sql
     SELECT * FROM user WHERE username="Amy"
     ```
   - **#{}** 可以防止 SQL 注入，因为它会对参数值进行预编译。

2. **${} 方式**：
   - **${}** 是直接将形参变量的值拼接到 SQL 中。
   - 例如，当实参 `username` 的值是 `"Amy"` 时，传入以下 Mapper 映射文件：
     ```xml
     <select id="findByName" parameterType="String" resultMap="studentResultMap">
         SELECT * FROM user WHERE username=${value}
     </select>
     ```
     SQL 将解析为：
     ```sql
     SELECT * FROM user WHERE username=Amy
     ```
   - **${}** 无法防止 SQL 注入，因为它直接将参数值拼接到 SQL 中。

3. **SQL 注入**：
   - **${}** 方式容易受到 SQL 注入攻击，因为参数值直接拼接到 SQL 中。
   - 例如，如果传入的参数是 `username = "' OR 1=1 OR '"`，则使用 **${}** 的 SQL 将变为：
     ```sql
     SELECT * FROM user WHERE username='' OR 1=1 OR '';
     ```
     这会暴露整个数据库内容。
   - **#{}** 方式则先进行预编译，无法通过非法参数改变原 SQL 的意图，因此可以防止 SQL 注入。

4. **适用场景**：
   - **#{}** 和 **${}** 都可以用于大部分场景。
   - 由于 SQL 注入的原因，推荐使用 **#{}**。
   - 只有在需要表示字段名、表名等情况下，才使用 **${}**。

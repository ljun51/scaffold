# rust

## 运算符
```rust
运算符     示例                              解释
+         expr + expr                      算术加法
+         trait + trait, 'a + trait        复合类型限制
-         expr - expr                      算术减法
-         - expr                           算术取负
*         expr * expr                      算术乘法
*         *expr                            解引用
*         *const type, *mut type           裸指针
/         expr / expr                      算术除法
%         expr % expr                      算术取余
=         var = expr, ident = type         赋值/等值
+=        var += expr                      算术加法与赋值
-=        var -= expr                      算术减法与赋值
*=        var *= expr                      算术乘法与赋值
/=        var /= expr                      算术除法与赋值
%=        var %= expr                      算术取余与赋值
　
==        expr == expr                     等于比较
!=        var != expr                      不等比较
>         expr > expr                      大于比较
<         expr < expr                      小于比较
>=        expr >= expr                     大于或等于比较
<=        expr <= expr                     小于或等于比较
　
&&        expr && expr                     逻辑与
||        expr || expr                     逻辑或
!         !expr                            按位非或逻辑非
　
&         expr & expr                      按位与
&         &expr, &mut expr                 借用
&         &type, &mut type,                借用指针类型
|         pat | pat                        模式选择
|         expr | expr                      按位或
^         expr ^ expr                      按位异或
<<        expr << expr                     左移
>>        expr >> expr                     右移
&=        var &= expr                      按位与和赋值
|=        var |= expr                      按位或与赋值
^=        var ^= expr                      按位异或与赋值
<<=       var <<= expr                     左移与赋值
>>=       var >>= expr                     右移与赋值
.         expr.ident                       成员访问
..        .., expr.., ..expr, expr..expr   右开区间范围
..        ..expr                           结构体更新语法
..=       ..=expr, expr..=expr             右闭区间范围模式
:         pat: type, ident: type           约束
:         ident: expr                      结构体字段初始化
:         'a: loop {...}                   循环标志
;         [type; len]                      固定大小的数组
=>        pat => expr                      匹配准备语法的部分
@         ident @ pat                      模式绑定
?         expr?                            错误传播
->        fn(...) -> type, |...| -> type   函数与闭包返回类型
```

## 所有权规则
* 每个值都有一个所有者（变量）。
* 值在任意时刻都只有一个所有者。
* 当所有者离开作用域时，其值将被丢弃（相当于执行垃圾回收）。

## 智能指针
智能指针最初存在于C++语言中，后被Rust借鉴。Rust语言为智能指针封装了两大trait——Deref和Drop，当变量实现了Deref和Drop后，就不再是普通变量了。实现Deref后，变量重载了解引用运算符“*”，可以当作普通引用来使用，必要时可以自动或手动实现解引用。实现Drop后，变量在超出作用域时会自动从堆中释放，当然还可自定义实现其他功能，如释放文件或网络连接，类似于C++语言中的析构函数。智能指针的特征如下。
* 智能指针在大部分情况下具有其所指向数据的所有权。
* 智能指针是一种数据结构，一般使用结构体来实现。
* 智能指针实现了Deref和Drop两大trait。

常见的智能指针有很多，而且你也可以实现自己需要的智能指针。用得比较频繁的智能指针或数据结构如下所示。注意，Cell< T >和RefCell< T >按上面的特征来说不算智能指针，但它们的概念又和智能指针太相似了，所以放到一起讨论。
* Box<T>是一种独占所有权的智能指针，指向存储在堆上且类型为T的数据。
* Rc<T>是一种共享所有权的计数智能指针，用于记录存储在堆上的值的引用数。
* Arc<T>是一种线程安全的共享所有权的计数智能指针，可用于多线程。
* Cell<T>是一种提供内部可变性的容器，不是智能指针，允许借用可变数据，编译时检查，参数T要求实现Copy trait。
* RefCell<T>也是一种提供内部可变性的容器，不是智能指针，允许借用可变数据，运行时检查，参数T不要求实现Copy trait。
* Weak<T>是一种与Rc<T>对应的弱引用类型，用于解决RefCell<T>中出现的循环引用。
* Cow<T>是一种写时复制的枚举体智能指针，我们使用Cow<T>主要是为了减少内存分配和复制，Cow<T>适用于读多写少的场景。

## Fn, FnMut, FnOnce
## dyn
## macro
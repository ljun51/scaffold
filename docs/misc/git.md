# git

## git合并多次提交为一次提交

```shell
    git config --global user.name lee
    git config --global user.email ljun51@outlook.com
    git config --global core.excludesFile '~/.gitignore'
    git config --global core.excludesFile "$Env:USERPROFILE\.gitignore"
    git config --global credential.helper store
```

特性分支上有多次提交记录，切换到master，合并成一次提交：
```shell    
    git merge --squash [branch name]
```

- git help gittutorial
- git help giteveryday
- git init 当前目录创建.git仓库
- git pull 更新代码库
- git -C ~/.SpaceVim pull . -C指定路径
- git remote show 显示可用仓库
- git remote show origin 显示仓库[origin]的信息
- git remote -v 显示远处仓库地址
- git remote set-url origin [http://testsys.chabc.net/cszj/finance-tax-platform](http://118.118.116.229:9800/lijun/abc123456-cszj.git) 变更远处仓库地址
- git checkout a 撤销工作区中文件a的修改
- git checkout master 切换到master分支
- git status 查看文件状态
- git status -s 或 git status --short 状态简览，类似svn status
- git add 跟踪文件：git add README
- git commit -am 'added new benchmarks' 跳过暂存(git add)区域提交
- git commit --amend
- git log --oneline --decorate --graph --all 显示提交历史、各个分支的指向及项目的分支分叉情况
- git log -p -2 显示最近两次提交内容的差异
- git diff --check 检查空白的空格和制表符
- git clone --depth=1 https://github.com/bcit-ci/CodeIgniter.git 下载最新版本
- git fetch --unshallow 获取完整历史信息
- git branch -d local 删除local分支
- git branch -a 查看所有分支
- git merge dev 合并dev分支
- git merge dev --squash 合并提交
- git pull <远程主机名> <远程分支名>:<本地分支名> 取回远程主机某个分支的更新，再与本地的指定分支合并
- git pull origin next:master 取回origin主机的next分支，与本地的master分支合并
- git pull origin next 远程分支是与当前分支合并，则冒号后面的部分可以省略
- git clone -b <branch> <remote_repo> 克隆分支
- find . -name .DS_Store -print0 | xargs -0 git rm -f --ignore-unmatch
- git rm只会将文件或目录从版本控制中移除，但并不会从以前的提交记录中移除文件或目录
- git rm -f 强制从Working Tree和Index中移除，不进行up-to-date检查
- git clean -f, git clean -df 清除未加入版本控制的文件，-d删除未加入版本控制的文件夹
- git push origin [name] 创建远程分支(本地分支**push**到远程)
- git push origin :heads/[name] 删除远程分支
- git tag -a v1.4 -m 'version 1.4' 打标签
- git push origin v0.1.2 # 将v0.1.2标签提交到git服务器
- git push origin –tags # 将本地所有标签一次性提交到git服务器
- git push origin --delete <branchName> 删除远程分支
- git remote prune origin／git fetch -p 删除本地缓存，远程已经删除的分支
- git reset --hard 取消本地修改
- git reset --hard a9eb0d57 回滚到某一次 commit
- git remote prune origin --dry-run 查看失效的远程分支

**git push**

git add --all

git commit -m "Initial commit"

git push -u origin master

git fetch origin master

git log -p master..origin/master

git merge origin/master

**文件 .gitignore 的格式规范如下：**

所有空行或者以 ＃ 开头的行都会被 Git 忽略。

可以使用标准的 glob 模式匹配。

匹配模式可以以（/）开头防止递归。

匹配模式可以以（/）结尾指定目录。

要忽略指定模式以外的文件或目录，可以在模式前加上惊叹号（!）取反。

所谓的 glob 模式是指 shell 所使用的简化了的正则表达式。 星号（*）匹配零个或多个任意字符；[abc] 匹配任何一个列在方括号中的字符（这个例子要么匹配一个 a，要么匹配一个 b，要么匹配一个 c）；问号（?）只匹配一个任意字符；如果在方括号中使用短划线分隔两个字符，表示所有在这两个字符范围内的都可以匹配（比如 [0-9] 表示匹配所有 0 到[…]”

[一篇极好的Git 总结 - 腾讯云技术社区 - SegmentFault 思否](https://segmentfault.com/a/1190000017114656)

[Git 实用指南 - 前端小册子 - SegmentFault 思否](https://segmentfault.com/a/1190000018688048)    
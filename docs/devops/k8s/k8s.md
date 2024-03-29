# kubernetes

## install

### minikube
```shell
minikube start --image-mirror-country=cn --kubernetes-version=v1.26.14 --memory 5120 --cpus=4

# Minikube 只能通过 NodePort 公开服务。EXTERNAL-IP 始终处于 pending 状态。运行以下命令以获取 WordPress 服务的 IP 地址：
minikube service wordpress --url
```

### kind
```shell
kind create cluster
kind delete cluster

# 3节点集群
kind create cluster --config cncf/k8s/kind-three-node-config.yaml

# kind 支持LoadBalancer
kubectl apply -f https://raw.githubusercontent.com/metallb/metallb/v0.13.7/config/manifests/metallb-native.yaml
kubectl apply -f cncf/k8s/metallb-config.yaml
```

### kubeadm

```shell
curl -fsSL https://mirrors.aliyun.com/kubernetes/apt/doc/apt-key.gpg | sudo gpg --dearmor -o /etc/apt/keyrings/kubernetes-apt-keyring.gpg
echo "deb [signed-by=/etc/apt/keyrings/kubernetes-apt-keyring.gpg] https://mirrors.aliyun.com/kubernetes/apt/ kubernetes-xenial main" | sudo tee /etc/apt/sources.list.d/kubernetes.list

# 指定版本
kubeadm init  --kubernetes-version=v1.26.14 --pod-network-cidr=10.244.0.0/16 --image-repository registry.aliyuncs.com/google_containers
```

## DNS
```shell
# Kubernetes 提供了一个自动为其它 Service 分配 DNS 名字的 DNS 插件 Service。 可以通过如下命令检查它是否在工作:
kubectl get services kube-dns --namespace=kube-system
```

## config
```shell
# 检查 kubectl 已知的位置和凭证
kubectl config view
# 使用 kubectl 代理
kubectl proxy --port=8080 &
# 使用 kubectl 代理，然后可以通过 curl，wget，或浏览器浏览 API
curl http://localhost:8080/api/
# 使用端口转发
kubectl port-forward svc/frontend 8080:80

# 监视状态
kubectl get pods -n dev --watch
watch kubectl get pods -n dev

# 使用 kubectl 进行故障排除
kubectl get - 列出资源
kubectl describe - 显示有关资源的详细信息
kubectl logs - 打印 Pod 中容器的日志
kubectl exec - 在 Pod 中的容器上执行命令

# busybox
kubectl run -i --tty --image busybox:1.28 dns-test --restart=Never --rm
kubectl run curl --image=radial/busyboxplus:curl -i --tty --rm
```

## events
```shell
# List recent events in the default namespace
kubectl events

# List recent events in all namespaces
kubectl events --all-namespaces

# List recent events for the specified pod, then wait for more events and list them as they arrive
kubectl events --for pod/web-pod-13je7 --watch

# List recent events in YAML format
kubectl events -oyaml

# List recent only events of type 'Warning' or 'Normal'
kubectl events --types=Warning,Normal
```

## namespace

```shell
# 创建 namespace
kubectl create namespace default-mem-example

# 设置名字空间偏好
kubectl config set-context --current --namespace=<名字空间名称>
# 验证
kubectl config view --minify | grep namespace:
# 将 kubectl 上下文设置为新集群
kubectl cluster-info --context kind-psa-wo-cluster-pss

# 删除 namespace
kubectl delete namespace default-mem-example

# 查看 LimitRange
kubectl get limitrange cpu-min-max-demo-lr --namespace=default-mem-example --output=yaml 

# 查看 ResourceQuota
kubectl get resourcequota mem-cpu-demo-rq --namespace=quota-mem-cpu-example --output=yaml

# 扩缩 Deployment
kubectl scale deployments/kubernetes-bootcamp --replicas=4
kubectl get pods -o wide

# 要更新应用程序的镜像版本到 v2，请使用 set image 子命令，后面给出 Deployment 名称和新的镜像版本：
kubectl set image deployments/kubernetes-bootcamp kubernetes-bootcamp=jocatalin/kubernetes-bootcamp:v2
# 要回滚 Deployment 到上一个工作的版本，请使用 rollout undo 子命令：
kubectl rollout undo deployments/kubernetes-bootcamp
```

并非所有对象都在名字空间中
```shell
# 位于名字空间中的资源
kubectl api-resources --namespaced=true
# 不在名字空间中的资源
kubectl api-resources --namespaced=false
```

## services

可以用不同的方式公开 Service：
* ClusterIP（默认）- 在集群的内部 IP 上公开 Service。这种类型使得 Service 只能从集群内访问。
* NodePort - 使用 NAT 在集群中每个选定 Node 的相同端口上公开 Service 。使用<NodeIP>:<NodePort> 从集群外部访问 Service。是 ClusterIP 的超集。
* LoadBalancer - 在当前云中创建一个外部负载均衡器（如果支持的话），并为 Service 分配一个固定的外部IP。是 NodePort 的超集。
* ExternalName - 将 Service 映射到 externalName 字段的内容（例如 foo.bar.example.com），通过返回带有该名称的 CNAME 记录实现。不设置任何类型的代理。这种类型需要 kube-dns 的 v1.7 或更高版本，或者 CoreDNS 的 0.8 或更高版本。

```shell
# 使用 kubectl expose 命令将 Pod 暴露给公网
kubectl expose deployment hello-node --type=LoadBalancer --port=8080
kubectl expose deployment/kubernetes-bootcamp --type="NodePort" --port 8080
```

## pods

### 在终端中显示应用
Pod 运行在隔离的、私有的网络中，因此我们需要代理访问它们，这样才能进行调试和交互。 为了做到这一点，我们将使用 kubectl proxy 命令在第二个终端中运行一个代理。 打开一个新的终端窗口，在这个新的终端中运行以下命令：
```shell
kubectl proxy
```

获取 Pod 命令并直接通过代理查询该 Pod。 要获取 Pod 命令并将其存到 POD_NAME 环境变量中，运行以下命令：
```shell
export POD_NAME="$(kubectl get pods -o go-template --template '{{range .items}}{{.metadata.name}}{{"\n"}}{{end}}')"
echo Name of the Pod: $POD_NAME
```

要查看应用的输出，运行 curl 请求：
```shell
curl http://localhost:8001/api/v1/namespaces/default/pods/$POD_NAME:8080/proxy/
```
URL 是到 Pod API 的路由。

### 查看容器日志
应用通常发送到标准输出的所有内容都成为 Pod 内容器的日志。 我们可以使用 kubectl logs 命令检索这些日志：
```shell
kubectl logs "$POD_NAME"
```
注：我们不需要指定容器名称，因为在 Pod 内只有一个容器。

在容器上执行命令
一旦 Pod 启动并运行，我们就可以直接在容器上执行命令。 为此，我们使用 exec 子命令，并将 Pod 的名称作为参数。让我们列出环境变量：
```shell
kubectl exec "$POD_NAME" -- env
```
另外值得一提的是，由于 Pod 中只有一个容器，所以容器本身的名称可以被省略。

接下来，让我们在 Pod 的容器中启动一个 bash 会话：
```shell
kubectl exec -ti $POD_NAME -- bash
```

## label
获取 Pod 的名称，然后存放到 POD_NAME 环境变量：
```shell
export POD_NAME="$(kubectl get pods -o go-template --template '{{range .items}}{{.metadata.name}}{{"\n"}}{{end}}')"
echo "Name of the Pod: $POD_NAME"
```
要应用一个新的标签，我们使用 label 子命令，接着是对象类型、对象名称和新的标签：
```shell
kubectl label pods "$POD_NAME" version=v1
```
这将会在我们的 Pod 上应用一个新标签（我们把应用版本锁定到 Pod 上），然后我们可以通过 describe pods 命令检查它：
```shell
kubectl describe pods "$POD_NAME"
```
我们可以看到现在标签已经被附加到我们的 Pod 上。我们可以通过新的标签来查询 Pod 列表：
```shell
kubectl get pods -l version=v1
```
我们看到了对应的 Pod。

## storageclasses
列出你的集群中的 StorageClass：
    
    kubectl get storageclass
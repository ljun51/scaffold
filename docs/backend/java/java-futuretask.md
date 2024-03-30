# FutureTask
可取消的异步计算。此类提供 Future 的基本实现，其中包含用于启动和取消计算、查询以查看计算是否完成以及取回计算结果的方法。只有在计算完成后才能取回结果;如果计算尚未完成，则 Get 方法将阻塞。计算完成后，无法重新启动或取消计算（除非使用 runAndReset 调用计算）。FutureTask 可用于包装 Callable 或 Runnable 对象。由于 FutureTask 实现了 Runnable，因此可以将 FutureTask 提交给 Executor 执行。除了用作独立类之外，此类还提供受保护的功能，这些功能在创建自定义任务类时可能很有用。FutureTask 的线程安全由 CAS 来保证。


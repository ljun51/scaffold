# Windows OS

## Windows 10激活
当重装了windows10专业版后，激活的教程如下：
- 鼠标右击“开始”
- 选择Windows PowerShell（管理员）
    ```powershell
    slmgr /ipk W269N-WFGWX-YVC9B-4J6C9-T83GX
    slmgr /skms kms.03k.org
    slmgr /ato
    ```

## Kill process by port
- Open Windows Terminal

    netstat -ano | findstr `:PORT`

- Type following to kill the process. kill PID
    
    taskkill /PID `<typeyourPIDhere>` /F
        
## ssh-copy-id
```powershell
type .ssh\id_ed25519.pub | ssh lee@hbook "cat >> .ssh/authorized_keys";
```   
    
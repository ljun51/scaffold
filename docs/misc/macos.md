# MacOS

## sshfs
To mount the directory /mnt/md0 from a remote server to the local directory md0:

> sshfs john@192.168.2.24:/home/john/Download/ ./hDownloads/ -ocache=no -onolocalcaches -ovolname=ssh

or:

> sshfs john@192.168.2.24:/home/john/Download/ ./hDownloads/
title="日常提醒"
content="常喝水，多站立，远离疾病，爱你的亲"
subtitle="该♨️一下了"
sound="Pon"
cmd=$(printf 'display notification "%s" with title "%s" subtitle "%s" sound name "%s"' "$content" "$title" "$subtitle" "$sound")
osascript -e "$cmd" 
say -v Ting-ting $content 

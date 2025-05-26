#!/bin/bash

# 패키지 업데이트
sudo yum update -y

# Docker 설치
sudo yum install -y docker

# Docker 시작 및 부팅 시 자동 시작
sudo systemctl start docker
sudo systemctl enable docker

# ec2-user를 docker 그룹에 추가 - ssh 재접속 시 sudo 없이
sudo usermod -aG docker ec2-user

# Docker Compose 설치
sudo curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" \
  -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose

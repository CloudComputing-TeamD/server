output "ec2_public_ip" {
  value = aws_instance.fit4u_instance.public_ip
}

output "ec2_eip" {
  value = aws_eip.fit4u_eip.public_ip
}

output "rds_endpoint" {
  value = aws_db_instance.fit4u_rds.endpoint
}

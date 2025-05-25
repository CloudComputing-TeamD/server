variable "region" {
  default = "us-east-1"
}

variable "db_name" {
  default = "fit4udb"
}

variable "db_username" {
  default = "admin"
}

variable "db_password" {
  description = "RDS admin password"
  type        = string
  sensitive   = true
}

variable "instance_type" {
  default = "t3.micro"
}

variable "db_allocated_storage" {
  default = 20
}

variable "rds_instance_class" {
  default = "db.t3.micro"
}

variable "ami_id" {
  description = "Amazon Linux 2023 AMI ID"
  default     = "ami-0953476d60561c955"
}

variable "key_name" {
  default = "fit4U_key"
}
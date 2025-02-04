provider "aws" {
  region = var.aws_region
}

resource "aws_vpc" "franquicias_vpc" {
  cidr_block = "10.0.0.0/16"
  tags = {
    Name = "franquicias-vpc"
  }
}

resource "aws_subnet" "franquicias_private_subnet_1a" {
  vpc_id            = aws_vpc.franquicias_vpc.id
  cidr_block        = "10.0.3.0/24"
  availability_zone = "us-east-1a"

  tags = {
    Name = "franquicias_private_subnet_1a"
  }
}

resource "aws_subnet" "franquicias_private_subnet_1b" {
  vpc_id            = aws_vpc.franquicias_vpc.id
  cidr_block        = "10.0.4.0/24"
  availability_zone = "us-east-1b"

  tags = {
    Name = "franquicias_private_subnet_1b"
  }
}

resource "aws_security_group" "franquicias_rds_sg" {
  vpc_id = aws_vpc.franquicias_vpc.id

  ingress {
    description      = "Allow MySQL traffic from ECS task"
    from_port   = 3306
    to_port     = 3306
    protocol    = "tcp"
    cidr_blocks = [
      aws_subnet.franquicias_private_subnet_1a.cidr_block,
      aws_subnet.franquicias_private_subnet_1b.cidr_block
    ]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "franquicias_rds_sg"
  }
}

resource "aws_db_instance" "franquicias_rds_instance" {
  allocated_storage    = 20
  storage_type         = "gp2"
  engine               = "mysql"
  engine_version       = "8.0"
  instance_class       = "db.t3.micro"
  db_name              = var.db_name
  username             = var.db_username
  password             = var.db_password
  port                 = 3306
  publicly_accessible  = false
  db_subnet_group_name = aws_db_subnet_group.franquicias_db_subnet_group.name
  vpc_security_group_ids = [aws_security_group.franquicias_rds_sg.id]

  skip_final_snapshot = true

  tags = {
    Name = "franquicias_rds_instance"
  }
}

resource "aws_db_subnet_group" "franquicias_db_subnet_group" {
  name       = "franquicias_db_subnet_group"
  subnet_ids = [
    aws_subnet.franquicias_private_subnet_1a.id,
    aws_subnet.franquicias_private_subnet_1b.id
  ]

  tags = {
    Name = "franquicias_db_subnet_group"
  }
}

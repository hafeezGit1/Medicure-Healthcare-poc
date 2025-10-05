# Jenkins Security Group
resource "aws_security_group" "jenkins" {
  name        = "medicure-jenkins-sg"
  description = "Security group for Jenkins"
  vpc_id      = aws_vpc.medicure_vpc.id

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "medicure-jenkins-sg"
  }
}

# EKS Cluster Security Group
resource "aws_security_group" "eks_cluster" {
  name        = "medicure-eks-sg"
  description = "Security group for EKS cluster"
  vpc_id      = aws_vpc.medicure_vpc.id

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "medicure-eks-sg"
  }
}
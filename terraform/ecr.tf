# ECR Repository
resource "aws_ecr_repository" "medicure" {
  name                 = "medicure-app"
  image_tag_mutability = "MUTABLE"

  image_scanning_configuration {
    scan_on_push = true
  }

  tags = {
    Name = "medicure-ecr"
  }
}
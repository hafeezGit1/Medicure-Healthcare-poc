output "jenkins_public_ip" {
  value = aws_instance.jenkins.public_ip
}

output "eks_cluster_endpoint" {
  value = aws_eks_cluster.medicure.endpoint
}

output "eks_cluster_name" {
  value = aws_eks_cluster.medicure.name
}

output "ecr_repository_url" {
  value = aws_ecr_repository.medicure.repository_url
}

output "vpc_id" {
  value = aws_vpc.medicure_vpc.id
}
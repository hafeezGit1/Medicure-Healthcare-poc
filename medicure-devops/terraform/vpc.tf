# VPC
resource "aws_vpc" "medicure_vpc" {
  cidr_block           = var.vpc_cidr
  enable_dns_hostnames = true
  enable_dns_support   = true

  tags = {
    Name = "medicure-vpc"
  }
}

# Internet Gateway
resource "aws_internet_gateway" "medicure_igw" {
  vpc_id = aws_vpc.medicure_vpc.id

  tags = {
    Name = "medicure-igw"
  }
}

# Public Subnets (2 for HA)
resource "aws_subnet" "public" {
  count                   = 2
  vpc_id                  = aws_vpc.medicure_vpc.id
  cidr_block              = "10.0.${count.index + 1}.0/24"
  availability_zone       = data.aws_availability_zones.available.names[count.index]
  map_public_ip_on_launch = true

  tags = {
    Name                        = "medicure-public-${count.index + 1}"
    "kubernetes.io/role/elb"    = "1"
    "kubernetes.io/cluster/${var.cluster_name}" = "shared"
  }
}

# Route Table
resource "aws_route_table" "public" {
  vpc_id = aws_vpc.medicure_vpc.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.medicure_igw.id
  }

  tags = {
    Name = "medicure-public-rt"
  }
}

# Route Table Association
resource "aws_route_table_association" "public" {
  count          = 2
  subnet_id      = aws_subnet.public[count.index].id
  route_table_id = aws_route_table.public.id
}

# Data source for AZs
data "aws_availability_zones" "available" {
  state = "available"
}
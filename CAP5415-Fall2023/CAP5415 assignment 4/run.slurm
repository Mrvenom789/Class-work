#!/bin/bash
#SBATCH --nodes=1
#SBATCH --ntasks-per-node=1
#SBATCH --cpus-per-task=12
#SBATCH --error=myjobresults-%J.err
#SBATCH --output=myjobresults-%J.out
#SBATCH --time=2:00:00
#SBATCH --job-name=PA4
#SBATCH --gres=gpu:1
#SBATCH --mem=12G
#SBATCH --partition=normal

echo "Slurm nodes assigned :$SLURM_JOB_NODELIST"
module load cuda/cuda-9.0
python testCNN.py
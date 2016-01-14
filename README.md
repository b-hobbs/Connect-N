# Connect-N

College project - Won first in head-to-head

A MinMax agent for Connect-N that uses the following heuristic:

Evaluate a board into a score
 * Counts the number of pieces out of n for diagonals and horizontal and vertical
 * Evaluation = myscore - oppents score
 * 1 out of n = number of occurrences * 1^4 
 * 2 out of n = number of occurrences * 2^4 
 * 3 out of n = number of occurrences * 3^4 
 * n out of n = number of occurrences * n^4 

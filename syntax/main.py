# -*- coding: utf-8 -*-
'''
启动程序的函数入口
'''
from tokenfile import tokenfile
from syntax import thread, match
from rulenode import rulenode
import sys
point = sys.stdout #记录输出指向

if __name__ == '__main__':

    file = tokenfile('tokenOut.txt')
    file.first()
    nodeset = []
    while match('thread', file.getcurrenttoken()[1]):
        nodeset.append(thread(rulenode('thread'), file))
    with open('syntaxOut.txt', 'w+') as outfile:
        sys.stdout = outfile
        for i in range(0,len(nodeset)):
            nodeset[i].printrulenode(0)
        sys.stdout = point
    print('语法分析结束，结果位于syntaxOut.txt')

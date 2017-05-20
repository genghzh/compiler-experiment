# -*- coding: utf-8 -*-
# pylint: disable=invalid-name
'''
启动程序的函数入口
'''
from function import add
from rulegraph import RuleGraph


if __name__ == '__main__':

    rule = RuleGraph(5)

    rule.statetransform(5, 'l')

    rule.getstate()

    try:
        file_object = open('test.txt', 'r')
        token = file_object.read(1)
        while token:
            print token
            token = file_object.read(1)
    finally:
        file_object.close()

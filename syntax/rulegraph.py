# -*- coding: utf-8 -*-
'''
将语法规则按照函数的方法实现
'''

class RuleGraph(object):
    """docstring for rulegraph"""
    def __init__(self, state):
        super(RuleGraph, self).__init__()
        self.current_state = state

    def statetransform(self, state, token):
        '''
        根据当前的状态和获得的token，决定程序下一步所处的状态
        并且能够对错误做出回应
        '''
        if state == 1:
            if token == 'l':
                self.current_state = 2
            else:
                self.current_state = 3

    def getstate(self):
        '''
        输出当前程序的状态
        '''
        print('currnet state: ', self.current_state)

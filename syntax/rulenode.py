# -*- encoding: utf-8 -*-
import sys
class rulenode(object):
    '''
    存储节点信息的类
    '''
    def __init__(self, type='', content=''):
        self._type = type
        self._content = content
        self._attribute = []
        self._childlist = []
        self._hasconstant = 0
    
    def gethasconstant(self):
        return self._hasconstant

    def setconstant(self, flag):
        self._hasconstant = flag

    def settype(self, type):
        self._type = type

    def getcontent(self):
        '''
        返回当前节点的content内容
        '''
        return self._content

    def setcontent(self, content):
        '''
        设置当前节点的
        '''
        self._content = content

    def addattribute(self, attri):
        '''
        为当前节点添加属性值
        也就是identifier
        '''
        self._attribute.append(attri)

    def addchildnode(self, rulenode):
        '''
        为当前节点添加子节点
        '''
        self._childlist.append(rulenode)

    def gettype(self):
        '''
        返回节点的类型
        '''
        return self._type

    def getchildlist(self):
        '''
        返回当前节点的子节点列表
        '''
        return self._childlist

    def getattribute(self):
        '''
        返回当前节点的属性list
        内容为identifier
        '''
        return self._attribute

    def printrulenode(self, grade):
        '''
        根据节点的不同type，根据不同的方式打印它的组成成分
        同时遍历它的子节点，调用子节点的打印方法
        '''
        if self.match('thread', self.gettype()):
            self.printT(grade)
            print('<thread id =', self.getattribute()[0], '>')
            self.printchildnode(grade + 1)
            self.printT(grade)
            print('</thread id =', self.getattribute()[1], '>')
        if self.match('feature', self.gettype()):
            self.printT(grade)
            print('<feature>')
            if not self.getcontent() == '':
                self.printT(grade + 1)
                print(self.getcontent())
            self.printchildnode(grade + 1)
            self.printT(grade)
            print('</feature>')
        if self.match('flow_spec', self.gettype()):
            self.printT(grade)
            print('<flow_spec>')
            if not self.getcontent() == '':
                self.printT(grade + 1)
                print(self.getcontent())
            self.printchildnode(grade + 1)
            self.printT(grade)
            print('</flow_spec>')
        if self.match('parameter', self.gettype()):
            self.printT(grade)
            print('<parameter>')
            self.printchildnode(grade + 1)
            self.printT(grade)
            print('</paramter>')
        if self.match('port_spec', self.gettype()):
            self.printT(grade)
            print('<port_spec>')
            self.printidentifier(grade + 1)
            self.printchildnode(grade + 1)
            self.printT(grade)
            print('</port_spec>')
        if self.match('port_type', self.gettype()):
            self.printT(grade)
            print('<port_type type =', self.getcontent(), '>')
            self.printidentifier(grade + 1)
            self.printchildnode(grade + 1)
            self.printT(grade)
            print('</port_type>')
        if self.match('IOtype', self.gettype()):
            self.printT(grade)
            print('<IOtypr>', self.getcontent(), '<IOtype>')
        if self.match('flow_source_spec', self.gettype()):
            self.printT(grade)
            print('<flow_source_spec>')
            self.printidentifier(grade + 1)
            self.printchildnode(grade + 1)
            self.printT(grade)
            print('</flow_source_spec>')
        if self.match('flow_sink_spec', self.gettype()):
            self.printT(grade)
            print('<flow_sink_spec>')
            self.printidentifier(grade + 1)
            self.printchildnode(grade + 1)
            self.printT(grade)
            print('</flow_sink_spec>')
        if self.match('flow_path_spec', self.gettype()):
            self.printT(grade)
            print('<flow_path_spec>')
            self.printidentifier(grade + 1)
            self.printchildnode(grade + 1)
            self.printT(grade)
            print('</flow_path_spec>')
        if self.match('association', self.gettype()):
            self.printT(grade)
            print('<association>')
            self.printidentifier(grade + 1)
            if self.getcontent() == 'none':
                self.printT(grade + 1)
                print(self.getcontent())
            if self.gethasconstant() == 1:
                self.printT(grade + 1)
                print('</constant>')
            self.printchildnode(grade + 1)
            self.printT(grade)
            print('</association>')
        if self.match('splitter', self.gettype()):
            self.printT(grade)
            print('<splitter>', self.getcontent(), '</splitter>')
        if self.match('decimal', self.gettype()):
            self.printT(grade)
            print('<decimal>', self.getcontent(), '</decimal>')
        if self.match('reference', self.gettype()):
            self.printT(grade)
            print('<reference>')
            self.printidentifier(grade + 1)
            self.printchildnode(grade + 1)
            self.printT(grade)
            print('</reference>')

    def match(self, certain, token):
        if certain == token:
            return True
        else:
            return False
    def printT(self, grade):
        '''
        控制输出结果的缩进
        '''
        for i in range(0, grade):
            sys.stdout.write('\t')
    def printchildnode(self, grade):
        for i in range(0, len(self.getchildlist())):
            self._childlist[i].printrulenode(grade)

    def printidentifier(self, grade):
        '''
        输出节点的全部identifier
        '''
        for i in range(0, len(self.getattribute())):
            self.printT(grade)
            print('<identifier>', self.getattribute()[i], '</identifier>')

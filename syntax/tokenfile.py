# -*- encoding: utf-8 -*-
import os

class tokenfile(object):
    def __init__(self, file_path_name):
        self._file_object = open(file_path_name, 'r')
        self._filesize = os.path.getsize(file_path_name)
        self._currenttoken = ['', '']

    def nexttoken(self):
        token = ''
        while True and self._filesize > 0:
            bytecode = self._file_object.read(1)
            self._filesize = self._filesize - 1
            if bytecode == '\n':
                break
            token = token + bytecode
        self._currenttoken = token.split(' ')
        return self.getcurrenttoken()

    def getfilesize(self):
        return self._filesize

    def getcurrenttoken(self):
        if self._filesize > 0:
            return self._currenttoken
        return ['stop', 'stop']

    def first(self):
        self.nexttoken()

    def __del__(self):
        self._file_object.close()

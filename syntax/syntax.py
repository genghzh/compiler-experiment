# -*- encoding: utf-8 -*-

from tokenfile import tokenfile
from rulenode import rulenode

def match(certain, token):
    if certain == token:
        return True
    else:
        return False

def thread(rnode, file_object):
    if match('thread', file_object.getcurrenttoken()[1]):
        rnode.settype('thread')
        file_object.nexttoken()
    if match('identifier', file_object.getcurrenttoken()[0]):
        rnode.addattribute(file_object.getcurrenttoken()[1])
        file_object.nexttoken()
    if match('features', file_object.getcurrenttoken()[1]):
        file_object.nexttoken()
        child = feature(rulenode('feature'), file_object)
        rnode.addchildnode(child)
    if match('flows', file_object.getcurrenttoken()[1]):
        file_object.nexttoken()
        child = flow_spec(rulenode('flow_spec'), file_object)
        rnode.addchildnode(child)
    if match('properties', file_object.getcurrenttoken()[1]):
        file_object.nexttoken()
        child = association(rulenode('association'), file_object)
        rnode.addchildnode(child)
    if match('end', file_object.getcurrenttoken()[1]):
        file_object.nexttoken()
    if match('identifier', file_object.getcurrenttoken()[0]):
        rnode.addattribute(file_object.getcurrenttoken()[1])
        file_object.nexttoken()
    if match(';', file_object.getcurrenttoken()[1]):
        if file_object.getfilesize() > 0:
            file_object.nexttoken()
    return rnode

def feature(rnode, file_object):
    if match('none', file_object.getcurrenttoken()[1]):
        rnode.setcontent('none')
        file_object.nexttoken()
    else:
        child = rulenode()
        if match('identifier', file_object.getcurrenttoken()[0]):
            child.addattribute(file_object.getcurrenttoken()[1])
            match(':', file_object.nexttoken()[1])
            file_object.nexttoken()
            child.addchildnode(IOtype(rulenode('IOtype'), file_object))
            if match('parameter', file_object.getcurrenttoken()[1]):
                child.settype('parameter')
                file_object.nexttoken()
                child = parameter(child, file_object)
            else:
                child.settype('port_spec')
                child.addchildnode(port_type(rulenode('port_type'), file_object))
        rnode.addchildnode(child)
    if match(';', file_object.getcurrenttoken()[1]):
        file_object.nexttoken()
    return rnode

def parameter(rnode, file_object):
    if match('{', file_object.getcurrenttoken()[1]):
        file_object.nexttoken()
        child = association(rulenode('association'), file_object)
        rnode.addchildnode(child)
        if match('}', file_object.nexttoken()[1]):
            file_object.nexttoken()
        if match(';', file_object.getcurrenttoken()[1]):
            file_object.nexttoken()
    else:
        child = reference(rulenode('reference'), file_object)
        rnode.addchildnode(child)
        if match('{', file_object.getcurrenttoken()[1]):
            file_object.nexttoken()
            child = association(rulenode('association'), file_object)
            rnode.addchildnode(child)
        if match('}', file_object.nexttoken()[1]):
            file_object.nexttoken()
        if match(';', file_object.getcurrenttoken()[1]):
            file_object.nexttoken()
    return rnode

def port_type(rnode, file_object):
    if match('data', file_object.getcurrenttoken()[1]):
        rnode.setcontent('data port')
        if match('port', file_object.nexttoken()[1]):
            if match('identifier', file_object.nexttoken()[0]):
                child = reference(rulenode('reference'), file_object)
                rnode.addchildnode(child)
    elif match('event', file_object.getcurrenttoken()[1]):
        if match('data', file_object.nexttoken()[1]):
            rnode.setcontent('event data port')
            if match('port', file_object.nexttoken()[1]):
                if match('identifier', file_object.nexttoken()[0]):
                    child = reference(rulenode('reference'), file_object)
                    rnode.addchildnode(child)
        elif match('port', file_object.getcurrenttoken()[1]):
            rnode.setcontent('event port')
            file_object.nexttoken()
    return rnode

def IOtype(rnode, file_object):
    if match('out', file_object.getcurrenttoken()[1]):
        rnode.setcontent('out')
        file_object.nexttoken()
    elif match('in', file_object.getcurrenttoken()[1]):
        if match('out', file_object.nexttoken()[1]):
            rnode.setcontent('in out')
            file_object.nexttoken()
        else:
            rnode.setcontent('in')
    return rnode

def flow_spec(rnode, file_object):
    if match('none', file_object.getcurrenttoken()[1]):
        rnode.settype('flow_spec')
        rnode.setcontent('none')
        file_object.nexttoken()
    elif match('identifier', file_object.getcurrenttoken()[0]):
        rnode.addattribute(file_object.getcurrenttoken()[1])
        if match(':', file_object.nexttoken()[1]):
            pass
        if match('flow', file_object.nexttoken()[1]):
            pass
        if match('source', file_object.nexttoken()[1]):
            rnode.settype('flow_source_spec')
            file_object.nexttoken()
            rnode = flow_source_spec(rnode, file_object)
        elif match('sink', file_object.getcurrenttoken()[1]):
            rnode.settype('flow_sink_spec')
            file_object.nexttoken()
            rnode = flow_sink_spec(rnode, file_object)
        elif match('path', file_object.getcurrenttoken()[1]):
            rnode.settype('flow_path_spec')
            file_object.nexttoken()
            rnode = flow_path_spec(rnode, file_object)
    if match(';', file_object.getcurrenttoken()[1]):
        file_object.nexttoken()
    return rnode

def flow_source_spec(rnode, file_object):
    if match('identifier', file_object.getcurrenttoken()[0]):
        rnode.addattribute(file_object.getcurrenttoken()[1])
    if match('{', file_object.nexttoken()[1]):
        file_object.nexttoken()
        child = association(rulenode('association'), file_object)
        rnode.addchildnode(child)
    if match('}', file_object.nexttoken()[1]):
        file_object.nexttoken()
    if match(';', file_object.getcurrenttoken()[1]):
        file_object.nexttoken()
    return rnode

def flow_sink_spec(rnode, file_object):
    if match('identifier', file_object.getcurrenttoken()[0]):
        rnode.addattribute(file_object.getcurrenttoken()[1])
    if match('{', file_object.nexttoken()[1]):
        file_object.nexttoken()
        child = association(rulenode('association'), file_object)
        rnode.addchildnode(child)
    if match('}', file_object.nexttoken()[1]):
        file_object.nexttoken()
    if match(';', file_object.getcurrenttoken()[1]):
        file_object.nexttoken()
    return rnode

def flow_path_spec(rnode, file_object):
    if match('identifier', file_object.getcurrenttoken()[0]):
        rnode.addattribute(file_object.getcurrenttoken()[1])
    if match('->', file_object.nexttoken()[1]):
        pass
    if match('identifier', file_object.nexttoken()[0]):
        rnode.addattribute(file_object.getcurrenttoken()[1])
    if match(';', file_object.nexttoken()[1]):
        file_object.nexttoken()
    return rnode

def association(rnode, file_object):
    if match('none', file_object.getcurrenttoken()[1]):
        rnode.setcontent('none')
        file_object.nexttoken()
    elif match('identifier', file_object.getcurrenttoken()[0]):
        rnode.addattribute(file_object.getcurrenttoken()[1])
        if match('::', file_object.nexttoken()[1]):
            if match('identifier', file_object.nexttoken()[0]):
                rnode.addattribute(file_object.getcurrenttoken()[1])
                file_object.nexttoken()
        child = splitter(rulenode('splitter'), file_object)
        rnode.addchildnode(child)
        if match('constant', file_object.getcurrenttoken()[1]):
            rnode.setconstant(1)
            file_object.nexttoken()
        if match('access', file_object.getcurrenttoken()[1]):
            file_object.nexttoken()
        if match('decimal', file_object.getcurrenttoken()[0]):
            rnode.addchildnode(rulenode('decimal', file_object.getcurrenttoken()[1]))
            file_object.nexttoken()
    if match(';', file_object.getcurrenttoken()[1]):
        file_object.nexttoken()
    return rnode

def splitter(rnode, file_object):
    if match('=>', file_object.getcurrenttoken()[1]):
        rnode.setcontent('=>')
    if match('+=>', file_object.getcurrenttoken()[1]):
        rnode.setcontent('+=>')
    file_object.nexttoken()
    return rnode

def reference(rnode, file_object):
    while True:
        if match('identifier', file_object.getcurrenttoken()[0]):
            rnode.addattribute(file_object.getcurrenttoken()[1])
        if match('::', file_object.nexttoken()[1]):
            file_object.nexttoken()
        if match(';', file_object.getcurrenttoken()[1]):
            break
    return rnode

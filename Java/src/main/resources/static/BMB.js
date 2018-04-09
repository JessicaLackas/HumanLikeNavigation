//
// Autogenerated by Thrift Compiler (0.9.3)
//
// DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
//


//HELPER FUNCTIONS AND STRUCTURES

BMB_setTargetPose_args = function(args) {
  this.pose = null;
  if (args) {
    if (args.pose !== undefined && args.pose !== null) {
      this.pose = new Pose(args.pose);
    }
  }
};
BMB_setTargetPose_args.prototype = {};
BMB_setTargetPose_args.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.STRUCT) {
        this.pose = new Pose();
        this.pose.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      case 0:
        input.skip(ftype);
        break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

BMB_setTargetPose_args.prototype.write = function(output) {
  output.writeStructBegin('BMB_setTargetPose_args');
  if (this.pose !== null && this.pose !== undefined) {
    output.writeFieldBegin('pose', Thrift.Type.STRUCT, 1);
    this.pose.write(output);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

BMB_setTargetPose_result = function(args) {
};
BMB_setTargetPose_result.prototype = {};
BMB_setTargetPose_result.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    input.skip(ftype);
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

BMB_setTargetPose_result.prototype.write = function(output) {
  output.writeStructBegin('BMB_setTargetPose_result');
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

BMB_setTarget_args = function(args) {
  this.name = null;
  if (args) {
    if (args.name !== undefined && args.name !== null) {
      this.name = args.name;
    }
  }
};
BMB_setTarget_args.prototype = {};
BMB_setTarget_args.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.STRING) {
        this.name = input.readString().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 0:
        input.skip(ftype);
        break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

BMB_setTarget_args.prototype.write = function(output) {
  output.writeStructBegin('BMB_setTarget_args');
  if (this.name !== null && this.name !== undefined) {
    output.writeFieldBegin('name', Thrift.Type.STRING, 1);
    output.writeString(this.name);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

BMB_setTarget_result = function(args) {
};
BMB_setTarget_result.prototype = {};
BMB_setTarget_result.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    input.skip(ftype);
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

BMB_setTarget_result.prototype.write = function(output) {
  output.writeStructBegin('BMB_setTarget_result');
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

BMB_savePose_args = function(args) {
  this.name = null;
  this.pose = null;
  if (args) {
    if (args.name !== undefined && args.name !== null) {
      this.name = args.name;
    }
    if (args.pose !== undefined && args.pose !== null) {
      this.pose = new Pose(args.pose);
    }
  }
};
BMB_savePose_args.prototype = {};
BMB_savePose_args.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.STRING) {
        this.name = input.readString().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 2:
      if (ftype == Thrift.Type.STRUCT) {
        this.pose = new Pose();
        this.pose.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

BMB_savePose_args.prototype.write = function(output) {
  output.writeStructBegin('BMB_savePose_args');
  if (this.name !== null && this.name !== undefined) {
    output.writeFieldBegin('name', Thrift.Type.STRING, 1);
    output.writeString(this.name);
    output.writeFieldEnd();
  }
  if (this.pose !== null && this.pose !== undefined) {
    output.writeFieldBegin('pose', Thrift.Type.STRUCT, 2);
    this.pose.write(output);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

BMB_savePose_result = function(args) {
};
BMB_savePose_result.prototype = {};
BMB_savePose_result.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    input.skip(ftype);
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

BMB_savePose_result.prototype.write = function(output) {
  output.writeStructBegin('BMB_savePose_result');
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

BMB_getTargetPose_args = function(args) {
};
BMB_getTargetPose_args.prototype = {};
BMB_getTargetPose_args.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    input.skip(ftype);
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

BMB_getTargetPose_args.prototype.write = function(output) {
  output.writeStructBegin('BMB_getTargetPose_args');
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

BMB_getTargetPose_result = function(args) {
  this.success = null;
  if (args) {
    if (args.success !== undefined && args.success !== null) {
      this.success = new Pose(args.success);
    }
  }
};
BMB_getTargetPose_result.prototype = {};
BMB_getTargetPose_result.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 0:
      if (ftype == Thrift.Type.STRUCT) {
        this.success = new Pose();
        this.success.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      case 0:
        input.skip(ftype);
        break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

BMB_getTargetPose_result.prototype.write = function(output) {
  output.writeStructBegin('BMB_getTargetPose_result');
  if (this.success !== null && this.success !== undefined) {
    output.writeFieldBegin('success', Thrift.Type.STRUCT, 0);
    this.success.write(output);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

BMB_getPose_args = function(args) {
};
BMB_getPose_args.prototype = {};
BMB_getPose_args.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    input.skip(ftype);
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

BMB_getPose_args.prototype.write = function(output) {
  output.writeStructBegin('BMB_getPose_args');
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

BMB_getPose_result = function(args) {
  this.success = null;
  if (args) {
    if (args.success !== undefined && args.success !== null) {
      this.success = new Pose(args.success);
    }
  }
};
BMB_getPose_result.prototype = {};
BMB_getPose_result.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 0:
      if (ftype == Thrift.Type.STRUCT) {
        this.success = new Pose();
        this.success.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      case 0:
        input.skip(ftype);
        break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

BMB_getPose_result.prototype.write = function(output) {
  output.writeStructBegin('BMB_getPose_result');
  if (this.success !== null && this.success !== undefined) {
    output.writeFieldBegin('success', Thrift.Type.STRUCT, 0);
    this.success.write(output);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

BMB_hasReachedTarget_args = function(args) {
};
BMB_hasReachedTarget_args.prototype = {};
BMB_hasReachedTarget_args.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    input.skip(ftype);
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

BMB_hasReachedTarget_args.prototype.write = function(output) {
  output.writeStructBegin('BMB_hasReachedTarget_args');
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

BMB_hasReachedTarget_result = function(args) {
  this.success = null;
  if (args) {
    if (args.success !== undefined && args.success !== null) {
      this.success = args.success;
    }
  }
};
BMB_hasReachedTarget_result.prototype = {};
BMB_hasReachedTarget_result.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 0:
      if (ftype == Thrift.Type.BOOL) {
        this.success = input.readBool().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 0:
        input.skip(ftype);
        break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

BMB_hasReachedTarget_result.prototype.write = function(output) {
  output.writeStructBegin('BMB_hasReachedTarget_result');
  if (this.success !== null && this.success !== undefined) {
    output.writeFieldBegin('success', Thrift.Type.BOOL, 0);
    output.writeBool(this.success);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

BMBClient = function(input, output) {
    this.input = input;
    this.output = (!output) ? input : output;
    this.seqid = 0;
};
BMBClient.prototype = {};
BMBClient.prototype.setTargetPose = function(pose, callback) {
  this.send_setTargetPose(pose, callback); 
  if (!callback) {
  this.recv_setTargetPose();
  }
};

BMBClient.prototype.send_setTargetPose = function(pose, callback) {
  this.output.writeMessageBegin('setTargetPose', Thrift.MessageType.CALL, this.seqid);
  var args = new BMB_setTargetPose_args();
  args.pose = pose;
  args.write(this.output);
  this.output.writeMessageEnd();
  if (callback) {
    var self = this;
    this.output.getTransport().flush(true, function() {
      var result = null;
      try {
        result = self.recv_setTargetPose();
      } catch (e) {
        result = e;
      }
      callback(result);
    });
  } else {
    return this.output.getTransport().flush();
  }
};

BMBClient.prototype.recv_setTargetPose = function() {
  var ret = this.input.readMessageBegin();
  var fname = ret.fname;
  var mtype = ret.mtype;
  var rseqid = ret.rseqid;
  if (mtype == Thrift.MessageType.EXCEPTION) {
    var x = new Thrift.TApplicationException();
    x.read(this.input);
    this.input.readMessageEnd();
    throw x;
  }
  var result = new BMB_setTargetPose_result();
  result.read(this.input);
  this.input.readMessageEnd();

  return;
};
BMBClient.prototype.setTarget = function(name, callback) {
  this.send_setTarget(name, callback); 
  if (!callback) {
  this.recv_setTarget();
  }
};

BMBClient.prototype.send_setTarget = function(name, callback) {
  this.output.writeMessageBegin('setTarget', Thrift.MessageType.CALL, this.seqid);
  var args = new BMB_setTarget_args();
  args.name = name;
  args.write(this.output);
  this.output.writeMessageEnd();
  if (callback) {
    var self = this;
    this.output.getTransport().flush(true, function() {
      var result = null;
      try {
        result = self.recv_setTarget();
      } catch (e) {
        result = e;
      }
      callback(result);
    });
  } else {
    return this.output.getTransport().flush();
  }
};

BMBClient.prototype.recv_setTarget = function() {
  var ret = this.input.readMessageBegin();
  var fname = ret.fname;
  var mtype = ret.mtype;
  var rseqid = ret.rseqid;
  if (mtype == Thrift.MessageType.EXCEPTION) {
    var x = new Thrift.TApplicationException();
    x.read(this.input);
    this.input.readMessageEnd();
    throw x;
  }
  var result = new BMB_setTarget_result();
  result.read(this.input);
  this.input.readMessageEnd();

  return;
};
BMBClient.prototype.savePose = function(name, pose, callback) {
  this.send_savePose(name, pose, callback); 
  if (!callback) {
  this.recv_savePose();
  }
};

BMBClient.prototype.send_savePose = function(name, pose, callback) {
  this.output.writeMessageBegin('savePose', Thrift.MessageType.CALL, this.seqid);
  var args = new BMB_savePose_args();
  args.name = name;
  args.pose = pose;
  args.write(this.output);
  this.output.writeMessageEnd();
  if (callback) {
    var self = this;
    this.output.getTransport().flush(true, function() {
      var result = null;
      try {
        result = self.recv_savePose();
      } catch (e) {
        result = e;
      }
      callback(result);
    });
  } else {
    return this.output.getTransport().flush();
  }
};

BMBClient.prototype.recv_savePose = function() {
  var ret = this.input.readMessageBegin();
  var fname = ret.fname;
  var mtype = ret.mtype;
  var rseqid = ret.rseqid;
  if (mtype == Thrift.MessageType.EXCEPTION) {
    var x = new Thrift.TApplicationException();
    x.read(this.input);
    this.input.readMessageEnd();
    throw x;
  }
  var result = new BMB_savePose_result();
  result.read(this.input);
  this.input.readMessageEnd();

  return;
};
BMBClient.prototype.getTargetPose = function(callback) {
  this.send_getTargetPose(callback); 
  if (!callback) {
    return this.recv_getTargetPose();
  }
};

BMBClient.prototype.send_getTargetPose = function(callback) {
  this.output.writeMessageBegin('getTargetPose', Thrift.MessageType.CALL, this.seqid);
  var args = new BMB_getTargetPose_args();
  args.write(this.output);
  this.output.writeMessageEnd();
  if (callback) {
    var self = this;
    this.output.getTransport().flush(true, function() {
      var result = null;
      try {
        result = self.recv_getTargetPose();
      } catch (e) {
        result = e;
      }
      callback(result);
    });
  } else {
    return this.output.getTransport().flush();
  }
};

BMBClient.prototype.recv_getTargetPose = function() {
  var ret = this.input.readMessageBegin();
  var fname = ret.fname;
  var mtype = ret.mtype;
  var rseqid = ret.rseqid;
  if (mtype == Thrift.MessageType.EXCEPTION) {
    var x = new Thrift.TApplicationException();
    x.read(this.input);
    this.input.readMessageEnd();
    throw x;
  }
  var result = new BMB_getTargetPose_result();
  result.read(this.input);
  this.input.readMessageEnd();

  if (null !== result.success) {
    return result.success;
  }
  throw 'getTargetPose failed: unknown result';
};
BMBClient.prototype.getPose = function(callback) {
  this.send_getPose(callback); 
  if (!callback) {
    return this.recv_getPose();
  }
};

BMBClient.prototype.send_getPose = function(callback) {
  this.output.writeMessageBegin('getPose', Thrift.MessageType.CALL, this.seqid);
  var args = new BMB_getPose_args();
  args.write(this.output);
  this.output.writeMessageEnd();
  if (callback) {
    var self = this;
    this.output.getTransport().flush(true, function() {
      var result = null;
      try {
        result = self.recv_getPose();
      } catch (e) {
        result = e;
      }
      callback(result);
    });
  } else {
    return this.output.getTransport().flush();
  }
};

BMBClient.prototype.recv_getPose = function() {
  var ret = this.input.readMessageBegin();
  var fname = ret.fname;
  var mtype = ret.mtype;
  var rseqid = ret.rseqid;
  if (mtype == Thrift.MessageType.EXCEPTION) {
    var x = new Thrift.TApplicationException();
    x.read(this.input);
    this.input.readMessageEnd();
    throw x;
  }
  var result = new BMB_getPose_result();
  result.read(this.input);
  this.input.readMessageEnd();

  if (null !== result.success) {
    return result.success;
  }
  throw 'getPose failed: unknown result';
};
BMBClient.prototype.hasReachedTarget = function(callback) {
  this.send_hasReachedTarget(callback); 
  if (!callback) {
    return this.recv_hasReachedTarget();
  }
};

BMBClient.prototype.send_hasReachedTarget = function(callback) {
  this.output.writeMessageBegin('hasReachedTarget', Thrift.MessageType.CALL, this.seqid);
  var args = new BMB_hasReachedTarget_args();
  args.write(this.output);
  this.output.writeMessageEnd();
  if (callback) {
    var self = this;
    this.output.getTransport().flush(true, function() {
      var result = null;
      try {
        result = self.recv_hasReachedTarget();
      } catch (e) {
        result = e;
      }
      callback(result);
    });
  } else {
    return this.output.getTransport().flush();
  }
};

BMBClient.prototype.recv_hasReachedTarget = function() {
  var ret = this.input.readMessageBegin();
  var fname = ret.fname;
  var mtype = ret.mtype;
  var rseqid = ret.rseqid;
  if (mtype == Thrift.MessageType.EXCEPTION) {
    var x = new Thrift.TApplicationException();
    x.read(this.input);
    this.input.readMessageEnd();
    throw x;
  }
  var result = new BMB_hasReachedTarget_result();
  result.read(this.input);
  this.input.readMessageEnd();

  if (null !== result.success) {
    return result.success;
  }
  throw 'hasReachedTarget failed: unknown result';
};

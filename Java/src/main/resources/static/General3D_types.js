//
// Autogenerated by Thrift Compiler (0.9.3)
//
// DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
//


Vector3d = function(args) {
  this.x = null;
  this.y = null;
  this.z = null;
  if (args) {
    if (args.x !== undefined && args.x !== null) {
      this.x = args.x;
    } else {
      throw new Thrift.TProtocolException(Thrift.TProtocolExceptionType.UNKNOWN, 'Required field x is unset!');
    }
    if (args.y !== undefined && args.y !== null) {
      this.y = args.y;
    } else {
      throw new Thrift.TProtocolException(Thrift.TProtocolExceptionType.UNKNOWN, 'Required field y is unset!');
    }
    if (args.z !== undefined && args.z !== null) {
      this.z = args.z;
    } else {
      throw new Thrift.TProtocolException(Thrift.TProtocolExceptionType.UNKNOWN, 'Required field z is unset!');
    }
  }
};
Vector3d.prototype = {};
Vector3d.prototype.read = function(input) {
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
      if (ftype == Thrift.Type.DOUBLE) {
        this.x = input.readDouble().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 2:
      if (ftype == Thrift.Type.DOUBLE) {
        this.y = input.readDouble().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 3:
      if (ftype == Thrift.Type.DOUBLE) {
        this.z = input.readDouble().value;
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

Vector3d.prototype.write = function(output) {
  output.writeStructBegin('Vector3d');
  if (this.x !== null && this.x !== undefined) {
    output.writeFieldBegin('x', Thrift.Type.DOUBLE, 1);
    output.writeDouble(this.x);
    output.writeFieldEnd();
  }
  if (this.y !== null && this.y !== undefined) {
    output.writeFieldBegin('y', Thrift.Type.DOUBLE, 2);
    output.writeDouble(this.y);
    output.writeFieldEnd();
  }
  if (this.z !== null && this.z !== undefined) {
    output.writeFieldBegin('z', Thrift.Type.DOUBLE, 3);
    output.writeDouble(this.z);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

Point3d = function(args) {
  this.x = null;
  this.y = null;
  this.z = null;
  if (args) {
    if (args.x !== undefined && args.x !== null) {
      this.x = args.x;
    } else {
      throw new Thrift.TProtocolException(Thrift.TProtocolExceptionType.UNKNOWN, 'Required field x is unset!');
    }
    if (args.y !== undefined && args.y !== null) {
      this.y = args.y;
    } else {
      throw new Thrift.TProtocolException(Thrift.TProtocolExceptionType.UNKNOWN, 'Required field y is unset!');
    }
    if (args.z !== undefined && args.z !== null) {
      this.z = args.z;
    } else {
      throw new Thrift.TProtocolException(Thrift.TProtocolExceptionType.UNKNOWN, 'Required field z is unset!');
    }
  }
};
Point3d.prototype = {};
Point3d.prototype.read = function(input) {
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
      if (ftype == Thrift.Type.DOUBLE) {
        this.x = input.readDouble().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 2:
      if (ftype == Thrift.Type.DOUBLE) {
        this.y = input.readDouble().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 3:
      if (ftype == Thrift.Type.DOUBLE) {
        this.z = input.readDouble().value;
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

Point3d.prototype.write = function(output) {
  output.writeStructBegin('Point3d');
  if (this.x !== null && this.x !== undefined) {
    output.writeFieldBegin('x', Thrift.Type.DOUBLE, 1);
    output.writeDouble(this.x);
    output.writeFieldEnd();
  }
  if (this.y !== null && this.y !== undefined) {
    output.writeFieldBegin('y', Thrift.Type.DOUBLE, 2);
    output.writeDouble(this.y);
    output.writeFieldEnd();
  }
  if (this.z !== null && this.z !== undefined) {
    output.writeFieldBegin('z', Thrift.Type.DOUBLE, 3);
    output.writeDouble(this.z);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

EulerAngles = function(args) {
  this.x = null;
  this.y = null;
  this.z = null;
  if (args) {
    if (args.x !== undefined && args.x !== null) {
      this.x = args.x;
    } else {
      throw new Thrift.TProtocolException(Thrift.TProtocolExceptionType.UNKNOWN, 'Required field x is unset!');
    }
    if (args.y !== undefined && args.y !== null) {
      this.y = args.y;
    } else {
      throw new Thrift.TProtocolException(Thrift.TProtocolExceptionType.UNKNOWN, 'Required field y is unset!');
    }
    if (args.z !== undefined && args.z !== null) {
      this.z = args.z;
    } else {
      throw new Thrift.TProtocolException(Thrift.TProtocolExceptionType.UNKNOWN, 'Required field z is unset!');
    }
  }
};
EulerAngles.prototype = {};
EulerAngles.prototype.read = function(input) {
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
      if (ftype == Thrift.Type.DOUBLE) {
        this.x = input.readDouble().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 2:
      if (ftype == Thrift.Type.DOUBLE) {
        this.y = input.readDouble().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 3:
      if (ftype == Thrift.Type.DOUBLE) {
        this.z = input.readDouble().value;
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

EulerAngles.prototype.write = function(output) {
  output.writeStructBegin('EulerAngles');
  if (this.x !== null && this.x !== undefined) {
    output.writeFieldBegin('x', Thrift.Type.DOUBLE, 1);
    output.writeDouble(this.x);
    output.writeFieldEnd();
  }
  if (this.y !== null && this.y !== undefined) {
    output.writeFieldBegin('y', Thrift.Type.DOUBLE, 2);
    output.writeDouble(this.y);
    output.writeFieldEnd();
  }
  if (this.z !== null && this.z !== undefined) {
    output.writeFieldBegin('z', Thrift.Type.DOUBLE, 3);
    output.writeDouble(this.z);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

Pose = function(args) {
  this.position = null;
  this.orientation = null;
  if (args) {
    if (args.position !== undefined && args.position !== null) {
      this.position = new Point3d(args.position);
    } else {
      throw new Thrift.TProtocolException(Thrift.TProtocolExceptionType.UNKNOWN, 'Required field position is unset!');
    }
    if (args.orientation !== undefined && args.orientation !== null) {
      this.orientation = new EulerAngles(args.orientation);
    } else {
      throw new Thrift.TProtocolException(Thrift.TProtocolExceptionType.UNKNOWN, 'Required field orientation is unset!');
    }
  }
};
Pose.prototype = {};
Pose.prototype.read = function(input) {
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
        this.position = new Point3d();
        this.position.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      case 2:
      if (ftype == Thrift.Type.STRUCT) {
        this.orientation = new EulerAngles();
        this.orientation.read(input);
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

Pose.prototype.write = function(output) {
  output.writeStructBegin('Pose');
  if (this.position !== null && this.position !== undefined) {
    output.writeFieldBegin('position', Thrift.Type.STRUCT, 1);
    this.position.write(output);
    output.writeFieldEnd();
  }
  if (this.orientation !== null && this.orientation !== undefined) {
    output.writeFieldBegin('orientation', Thrift.Type.STRUCT, 2);
    this.orientation.write(output);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

Quaternion = function(args) {
  this.x = null;
  this.y = null;
  this.z = null;
  this.w = null;
  if (args) {
    if (args.x !== undefined && args.x !== null) {
      this.x = args.x;
    } else {
      throw new Thrift.TProtocolException(Thrift.TProtocolExceptionType.UNKNOWN, 'Required field x is unset!');
    }
    if (args.y !== undefined && args.y !== null) {
      this.y = args.y;
    } else {
      throw new Thrift.TProtocolException(Thrift.TProtocolExceptionType.UNKNOWN, 'Required field y is unset!');
    }
    if (args.z !== undefined && args.z !== null) {
      this.z = args.z;
    } else {
      throw new Thrift.TProtocolException(Thrift.TProtocolExceptionType.UNKNOWN, 'Required field z is unset!');
    }
    if (args.w !== undefined && args.w !== null) {
      this.w = args.w;
    } else {
      throw new Thrift.TProtocolException(Thrift.TProtocolExceptionType.UNKNOWN, 'Required field w is unset!');
    }
  }
};
Quaternion.prototype = {};
Quaternion.prototype.read = function(input) {
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
      if (ftype == Thrift.Type.DOUBLE) {
        this.x = input.readDouble().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 2:
      if (ftype == Thrift.Type.DOUBLE) {
        this.y = input.readDouble().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 3:
      if (ftype == Thrift.Type.DOUBLE) {
        this.z = input.readDouble().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 4:
      if (ftype == Thrift.Type.DOUBLE) {
        this.w = input.readDouble().value;
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

Quaternion.prototype.write = function(output) {
  output.writeStructBegin('Quaternion');
  if (this.x !== null && this.x !== undefined) {
    output.writeFieldBegin('x', Thrift.Type.DOUBLE, 1);
    output.writeDouble(this.x);
    output.writeFieldEnd();
  }
  if (this.y !== null && this.y !== undefined) {
    output.writeFieldBegin('y', Thrift.Type.DOUBLE, 2);
    output.writeDouble(this.y);
    output.writeFieldEnd();
  }
  if (this.z !== null && this.z !== undefined) {
    output.writeFieldBegin('z', Thrift.Type.DOUBLE, 3);
    output.writeDouble(this.z);
    output.writeFieldEnd();
  }
  if (this.w !== null && this.w !== undefined) {
    output.writeFieldBegin('w', Thrift.Type.DOUBLE, 4);
    output.writeDouble(this.w);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

Transform3D = function(args) {
  this.position = null;
  this.orientation = null;
  this.source_frame = null;
  this.target_frame = null;
  this.timestamp = null;
  if (args) {
    if (args.position !== undefined && args.position !== null) {
      this.position = new Vector3d(args.position);
    } else {
      throw new Thrift.TProtocolException(Thrift.TProtocolExceptionType.UNKNOWN, 'Required field position is unset!');
    }
    if (args.orientation !== undefined && args.orientation !== null) {
      this.orientation = new Quaternion(args.orientation);
    } else {
      throw new Thrift.TProtocolException(Thrift.TProtocolExceptionType.UNKNOWN, 'Required field orientation is unset!');
    }
    if (args.source_frame !== undefined && args.source_frame !== null) {
      this.source_frame = args.source_frame;
    } else {
      throw new Thrift.TProtocolException(Thrift.TProtocolExceptionType.UNKNOWN, 'Required field source_frame is unset!');
    }
    if (args.target_frame !== undefined && args.target_frame !== null) {
      this.target_frame = args.target_frame;
    } else {
      throw new Thrift.TProtocolException(Thrift.TProtocolExceptionType.UNKNOWN, 'Required field target_frame is unset!');
    }
    if (args.timestamp !== undefined && args.timestamp !== null) {
      this.timestamp = args.timestamp;
    } else {
      throw new Thrift.TProtocolException(Thrift.TProtocolExceptionType.UNKNOWN, 'Required field timestamp is unset!');
    }
  }
};
Transform3D.prototype = {};
Transform3D.prototype.read = function(input) {
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
        this.position = new Vector3d();
        this.position.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      case 2:
      if (ftype == Thrift.Type.STRUCT) {
        this.orientation = new Quaternion();
        this.orientation.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      case 3:
      if (ftype == Thrift.Type.STRING) {
        this.source_frame = input.readString().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 4:
      if (ftype == Thrift.Type.STRING) {
        this.target_frame = input.readString().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 5:
      if (ftype == Thrift.Type.I64) {
        this.timestamp = input.readI64().value;
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

Transform3D.prototype.write = function(output) {
  output.writeStructBegin('Transform3D');
  if (this.position !== null && this.position !== undefined) {
    output.writeFieldBegin('position', Thrift.Type.STRUCT, 1);
    this.position.write(output);
    output.writeFieldEnd();
  }
  if (this.orientation !== null && this.orientation !== undefined) {
    output.writeFieldBegin('orientation', Thrift.Type.STRUCT, 2);
    this.orientation.write(output);
    output.writeFieldEnd();
  }
  if (this.source_frame !== null && this.source_frame !== undefined) {
    output.writeFieldBegin('source_frame', Thrift.Type.STRING, 3);
    output.writeString(this.source_frame);
    output.writeFieldEnd();
  }
  if (this.target_frame !== null && this.target_frame !== undefined) {
    output.writeFieldBegin('target_frame', Thrift.Type.STRING, 4);
    output.writeString(this.target_frame);
    output.writeFieldEnd();
  }
  if (this.timestamp !== null && this.timestamp !== undefined) {
    output.writeFieldBegin('timestamp', Thrift.Type.I64, 5);
    output.writeI64(this.timestamp);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};


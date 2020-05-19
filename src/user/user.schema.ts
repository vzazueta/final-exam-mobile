import * as mongoose from 'mongoose';

export const UserSchema = new mongoose.Schema({
    id: {type: String, requires: true},
    name: { type: String, required: true },
    birthdate: { type: Date, required: true },
    password: { type: String, required: true },
});

export interface User extends mongoose.Document {
    readonly id: String;
    readonly name: String;
    readonly birthdate: Date;
    readonly password: String;
  }


export class UserDTO  {
    readonly id: String;
    readonly name: String;
    readonly birthdate: Date;
    readonly password: String;
}
  
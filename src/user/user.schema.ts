import * as mongoose from 'mongoose';

export const UserSchema = new mongoose.Schema({
    id: {type: String, requires: true},
    name: { type: String, required: true },
    birthdate: { type: Date, required: true },
    password: { type: String, required: true },
    gender: { type: String, required: true },
    allergic: { type: Boolean, required: true },
    allergic_description: { type: String },
});

export interface User extends mongoose.Document {
    readonly id: String;
    readonly name: String;
    readonly birthdate: Date;
    readonly password: String;
    readonly gender: String;
    readonly allergic: boolean;
    readonly allergic_description: String;
  }


export class UserDTO  {
    readonly id: String;
    readonly name: String;
    readonly birthdate: Date;
    readonly password: String;
    readonly gender: String;
    readonly allergic: boolean;
    readonly allergic_description: String;
}
  
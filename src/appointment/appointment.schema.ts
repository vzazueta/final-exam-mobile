import * as mongoose from 'mongoose';

export const AppointmentSchema = new mongoose.Schema({
    id: {type:String, required: true},
    user_id: {type: String, requires: true},
    name: { type: String, required: true },
    age: { type: Number, required: true },
    gender: { type: String, required: true },
    alergic: { type: String, required: true },
    description: { type: String, required: true },
});

export interface Appointment extends mongoose.Document {
    readonly id: String;
    readonly user_id: String;
    readonly name: String;
    readonly age: Number;
    readonly gender: String;
    readonly alergic: String;
    readonly description: String;
  }


export class AppointmentDTO  {
    readonly id: String;
    readonly user_id: String;
    readonly name: String;
    readonly age: Number;
    readonly gender: String;
    readonly alergic: String;
    readonly description: String;
}
  
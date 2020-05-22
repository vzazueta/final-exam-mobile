import * as mongoose from 'mongoose';

export const AppointmentSchema = new mongoose.Schema({
    user_id: {type: String, requires: true},
    created_by: { type: String },
    date: { type: Date, required: true },
    description: { type: String, required: true },
});

export interface Appointment extends mongoose.Document {
    readonly user_id: String;
    readonly created_by: String;
    readonly date: Date;
    readonly description: String;
  }


export class AppointmentDTO  {
    readonly user_id: String;
    readonly created_by: String;
    readonly date: Date;
    readonly description: String;
}
  
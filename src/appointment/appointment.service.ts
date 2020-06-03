import { Injectable } from '@nestjs/common';
import { Model } from 'mongoose';
import { InjectModel } from '@nestjs/mongoose';
import { AppointmentDTO, Appointment } from './appointment.schema';

@Injectable()
export class AppointmentService {
    constructor(@InjectModel('Appointment') private readonly model: Model<Appointment>) {
    }

    async add(appointmentDTO: AppointmentDTO): Promise<Appointment> {
        const obj = new this.model(appointmentDTO);
        return await obj.save();
    }

    async getOne(id): Promise<Appointment> {
        const obj = await this.model.find({id:id}).exec();
        return obj[0];
    }

    async get(): Promise<Appointment[]> {
        const obj = await this.model.find().exec();
        return obj;
    }

    async edit(id, appointmentDTO: AppointmentDTO): Promise<Appointment> {
        const obj = await this.model.findByIdAndUpdate(id, appointmentDTO, {new: true});
        return obj;
    }

    async delete(id): Promise<Appointment> {
        const obj = await this.model.findByIdAndDelete(id);
        return obj;
    }
}

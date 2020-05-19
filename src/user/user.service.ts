import { Injectable } from '@nestjs/common';
import { Model } from 'mongoose';
import { InjectModel } from '@nestjs/mongoose';
import { User, UserDTO } from './user.schema';

@Injectable()
export class UserService {
    constructor(@InjectModel('User') private readonly model: Model<User>) {
    }

    async add(userDTO: UserDTO): Promise<User> {
        const obj = new this.model(userDTO);
        return await obj.save();
    }

    async getOne(id): Promise<User> {
        const obj = await this.model.find({id:id}).exec();
        return obj[0];
    }

    async get(): Promise<User[]> {
        const obj = await this.model.find().exec();
        return obj;
    }

    async edit(id, userDTO: UserDTO): Promise<User> {
        const obj = await this.model.findOneAndUpdate({id:id}, userDTO, {new: true});
        return obj;
    }

    async delete(id): Promise<User> {
        const obj = await this.model.findOneAndDelete({id:id});
        return obj;
    }
}

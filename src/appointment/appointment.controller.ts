import { Controller, Post, Body, Get, Param, NotFoundException, Put, Delete } from '@nestjs/common';
import { AppointmentService } from './appointment.service';
import { AppointmentDTO } from './appointment.schema';

@Controller('appointment')
export class AppointmentController {
    constructor(private service: AppointmentService){}

    @Post()
    async add(@Body() dto: AppointmentDTO){
        return await this.service.add(dto);
    }

    @Get()
    async get(){
        return await this.service.get();
    }

    @Get(":id")
    async getOne(@Param('id') id) {
        const user = await this.service.getOne(id);
        if (!user) {
            throw new NotFoundException('Object does not exist!');
        }
        return user;
    }

    @Put(":id")
    async edit(@Param('id') id, @Body() dto: AppointmentDTO){
        const editedUser = await this.service.edit(id, dto);
        if (!editedUser) {
            throw new NotFoundException('Object does not exist!');
        }
        return editedUser;
    }

    @Delete(":id")
    async delete(@Param('id') id){
        const deletedUser = await this.service.delete(id);
        if (!deletedUser) {
            throw new NotFoundException('Object does not exist!');
        }
        return deletedUser;
    }

}

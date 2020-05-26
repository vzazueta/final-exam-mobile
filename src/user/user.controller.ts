import { Controller, Post, Body, Get, Param, NotFoundException, Put, Delete } from '@nestjs/common';
import { UserDTO } from './user.schema';
import { UserService } from './user.service';


@Controller('user')
export class UserController {

    constructor(private service: UserService){}

    @Post()
    async add(@Body() dto: UserDTO){
        return await this.service.add(dto);
    }

    @Get()
    async get(){
        return await this.service.get();
    }

    @Get("login")
    async login(@Body() data: {id: String, pass: String}) {
        const user = await this.service.getOne(data.id);
        if(!user || user.password != data.pass){
            return {};
        }
        return user;
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
    async edit(@Param('id') id, @Body() dto: UserDTO){
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

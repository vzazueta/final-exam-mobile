import { Module } from '@nestjs/common';
import { MongooseModule } from '@nestjs/mongoose';
import { UserModule } from './user/user.module';
import { AppointmentModule } from './appointment/appointment.module';
require('dotenv').config();

@Module({
  imports: [
    MongooseModule.forRoot(process.env.MONGODB_URI || 'mongodb://localhost/final-exam-mobile', { useNewUrlParse: true, useUnifiedTopology: true}),
    UserModule, AppointmentModule],
})
export class AppModule {}

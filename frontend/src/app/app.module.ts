import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {CoreModule} from './core/core.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {ProjectsModule} from './projects/projects.module';
import {Interceptor} from './interceptor';
import {UsersModule} from './users/users.module';
import {ToastrModule} from 'ngx-toastr';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {DialogsModule} from './dialogs/dialogs.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    CoreModule,
    UsersModule,
    ProjectsModule,
    HttpClientModule,
    ReactiveFormsModule,
    ToastrModule.forRoot(),
    NgbModule,
    DialogsModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: Interceptor,
      multi: true
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

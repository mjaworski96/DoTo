import { DOCUMENT } from '@angular/common';
import { Component, ElementRef, Inject, LOCALE_ID, ViewChild } from '@angular/core';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  @ViewChild('title', {static: false})
  translatedTitle: ElementRef;

  constructor(private titleService: Title,
    @Inject(DOCUMENT) private document: Document,
    @Inject(LOCALE_ID) private locale: string) {}

  ngAfterViewInit(): void {
    this.titleService.setTitle(this.translatedTitle.nativeElement.innerHTML);
    this.document.documentElement.lang = this.locale;
  }
}

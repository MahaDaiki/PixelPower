import { Component, ElementRef, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { animate, style, transition, trigger } from '@angular/animations';
import {AuthService} from '../../service/auth.service';

@Component({
  selector: 'app-home-page',
  standalone: false,
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css',
  animations: [
    trigger('zoomAnimation', [
      transition(':leave', [
        style({ transform: 'scale(1)', opacity: 1 }),
        animate('800ms ease-in', style({
          transform: 'scale(10)',
          opacity: 0
        }))
      ])
    ])
  ]
})
export class HomePageComponent {
  @ViewChild('container') container!: ElementRef;
  isAnimating = false;
  targetElement = '';

  constructor(private router: Router, private authService: AuthService) {}

  navigateTo(element: string, route: string): void {
    if (this.isAnimating) return;

    this.isAnimating = true;
    this.targetElement = element;


    setTimeout(() => {
      this.router.navigate([route]);
    }, 750);
  }


  getZoomStyle(elementId: string): object {
    if (this.targetElement !== elementId) return {};

    const element = document.getElementById(elementId);
    if (!element) return {};


    if (!this.container?.nativeElement) return {};

    const rect = element.getBoundingClientRect();
    const containerRect = this.container.nativeElement.getBoundingClientRect();

    const originX = ((rect.left + rect.width / 2) - containerRect.left) / containerRect.width * 100;
    const originY = ((rect.top + rect.height / 2) - containerRect.top) / containerRect.height * 100;

    return {
      'transform-origin': `${originX}% ${originY}%`
    };
  }

  isAuthenticated(): boolean {
    return this.authService.isAuthenticated();
  }


}

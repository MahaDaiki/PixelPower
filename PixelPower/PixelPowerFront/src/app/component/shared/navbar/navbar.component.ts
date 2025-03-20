import { Component } from '@angular/core';
import {AuthService} from '../../../service/auth.service';
import {Router} from '@angular/router';
import {animate, state, style, transition, trigger} from '@angular/animations';

@Component({
  selector: 'app-navbar',
  standalone: false,
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
  animations: [
    trigger("fadeIn", [
      state(
        "void",
        style({
          opacity: 0,
        }),
      ),
      transition(":enter", [
        animate(
          "0.5s ease-out",
          style({
            opacity: 1,
          }),
        ),
      ]),
    ]),
    trigger("glitchText", [
      state(
        "normal",
        style({
          transform: "translateX(0)",
        }),
      ),
      state(
        "glitch",
        style({
          transform: "translateX(0)",
        }),
      ),
      transition("normal <=> glitch", [
        animate("0.1s", style({ transform: "translateX(10px)" })),
        animate("0.1s", style({ transform: "translateX(-10px)" })),
        animate("0.1s", style({ transform: "translateX(5px)" })),
        animate("0.1s", style({ transform: "translateX(0)" })),
      ]),
    ]),
  ],
})
export class NavbarComponent {
  isMenuOpen = false;

  constructor(private authService: AuthService, private router: Router) {}

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  isAuthenticated(): boolean {
    return this.authService.isAuthenticated();
  }

  logout(): void {
    this.authService.logout();
  }

}

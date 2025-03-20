import { Component } from '@angular/core';
import {animate, state, style, transition, trigger} from "@angular/animations";

@Component({
  selector: 'app-not-found',
  standalone: false,
  templateUrl: './not-found.component.html',
  styleUrl: './not-found.component.css',
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
export class NotFoundComponent {
  timestamp = new Date()
  glitchState = "normal"

  constructor() {}

  ngOnInit(): void {
    this.startRandomGlitches()
  }

  startRandomGlitches(): void {
    const triggerGlitch = () => {
      this.glitchState = "glitch"
      setTimeout(() => {
        this.glitchState = "normal"
      }, 500)

      const nextGlitchDelay = 2000 + Math.random() * 5000
      setTimeout(triggerGlitch, nextGlitchDelay)
    }

    setTimeout(triggerGlitch, 2000)
  }

}

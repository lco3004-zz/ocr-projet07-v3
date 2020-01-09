import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {OuvragesDisponiblesComponent} from './ouvrages-disponibles.component';

describe('OuvragesDisponiblesComponent', () => {
  let component: OuvragesDisponiblesComponent;
  let fixture: ComponentFixture<OuvragesDisponiblesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OuvragesDisponiblesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OuvragesDisponiblesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

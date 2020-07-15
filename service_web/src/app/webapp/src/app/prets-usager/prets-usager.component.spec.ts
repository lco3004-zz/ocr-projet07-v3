import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {PretsUsagerComponent} from './prets-usager.component';

describe('PretsUsagerComponent', () => {
  let component: PretsUsagerComponent;
  let fixture: ComponentFixture<PretsUsagerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PretsUsagerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PretsUsagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
